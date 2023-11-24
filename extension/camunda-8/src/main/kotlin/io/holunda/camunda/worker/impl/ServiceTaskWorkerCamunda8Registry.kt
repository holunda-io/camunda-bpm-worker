package io.holunda.camunda.worker.impl

import io.holunda.camunda.worker.ServiceTaskWorker
import mu.KLogging
import org.springframework.beans.factory.config.BeanPostProcessor

/**
 * Worker Registry.
 */
class ServiceTaskWorkerCamunda8Registry : BeanPostProcessor {

  companion object : KLogging()

  private val workers: MutableList<ServiceTaskWorker> = mutableListOf()
  val registeredWorkers: List<ServiceTaskWorker> by lazy { workers.toList() }

  override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any {
    if (bean is ServiceTaskWorker) {
      if (TopicUtil.hasServiceType(bean)) {
        logger.info { "Found external task service: ${bean::class.java.canonicalName}, $beanName" }
        workers.add(bean)
      }
    }
    return bean
  }
}

