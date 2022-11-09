package io.holunda.camunda.worker.impl

import impl.TopicUtil
import io.holunda.camunda.bpm.data.CamundaBpmData.reader
import io.holunda.camunda.bpm.data.CamundaBpmData.writer
import io.holunda.camunda.worker.ServiceTaskWorker
import mu.KLogging
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.stereotype.Component

/**
 * Java Delegate Service Task Worker.
 */
class JavaDelegateServiceTaskWorkerRegistrar : BeanPostProcessor {

  companion object : KLogging()

  fun register(serviceTaskWorker: ServiceTaskWorker): JavaDelegate {
    return JavaDelegate { delegateExecution ->
      serviceTaskWorker.execute(reader(delegateExecution), writer(delegateExecution))
    }
  }

  override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any {
    if (bean is ServiceTaskWorker && !TopicUtil.hasTopic(bean)) {
      logger.info { "Found java delegate service: ${bean::class.java.canonicalName}, $beanName" }
      return register(serviceTaskWorker = bean)
    }
    return bean
  }
}
