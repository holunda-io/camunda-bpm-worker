package io.holunda.camunda.worker.impl

import io.holunda.camunda.bpm.data.CamundaBpmData.reader
import io.holunda.camunda.bpm.data.CamundaBpmData.writer
import io.holunda.camunda.worker.ServiceTaskWorker
import mu.KLogging
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.beans.factory.config.BeanPostProcessor

/**
 * Service Task Worker Registration for Camunda 7.
 */
class ServiceTaskWorkerCamunda7Registry(
  private val properties: CamundaWorker7Properties
) : BeanPostProcessor {

  companion object : KLogging()

  private val workers: MutableList<ServiceTaskWorker> = mutableListOf()
  val registeredWorkers: List<ServiceTaskWorker> by lazy { workers.toList() }

  override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any {
    if (bean is ServiceTaskWorker) {
      return when (properties.taskMode) {
        ExternalTaskMode.CLIENT -> registerExternalTaskWorker(serviceTaskWorker = bean)
        ExternalTaskMode.SERVICE -> registerExternalTaskWorker(serviceTaskWorker = bean)
        ExternalTaskMode.DELEGATE -> registerJavaDelegate(serviceTaskWorker = bean)
      }
    }
    return bean
  }

  private fun registerJavaDelegate(serviceTaskWorker: ServiceTaskWorker): JavaDelegate {
    return JavaDelegate { delegateExecution ->
      serviceTaskWorker.execute(reader(delegateExecution), writer(delegateExecution))
    }
  }

  private fun registerExternalTaskWorker(serviceTaskWorker: ServiceTaskWorker): ServiceTaskWorker {
    if (TopicUtil.hasServiceType(serviceTaskWorker)) {
      val type = TopicUtil.retrieveServiceType(serviceTaskWorker)
      logger.info { "Found external task service for type $type: ${serviceTaskWorker::class.java.canonicalName}." }
      workers.add(serviceTaskWorker)
    }
    return serviceTaskWorker
  }

}

