package io.holunda.camunda.worker.impl

import impl.TopicUtil
import io.holunda.camunda.bpm.data.CamundaBpmData.reader
import io.holunda.camunda.bpm.data.CamundaBpmData.writer
import io.holunda.camunda.bpm.data.writer.VariableWriter
import io.holunda.camunda.worker.ServiceTaskWorker
import mu.KLogging
import org.camunda.bpm.engine.ExternalTaskService
import org.camunda.bpm.engine.delegate.BpmnError
import org.camunda.bpm.engine.variable.Variables
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.stereotype.Component


class ExternalTaskServiceTaskWorkerRegistrar(
  private val externalTaskService: ExternalTaskService,
): BeanPostProcessor {

  companion object: KLogging()

  fun register(serviceTaskWorker: ServiceTaskWorker) {
    val workerId = this.javaClass.canonicalName
    externalTaskService
      .fetchAndLock(10, workerId)
      .topic(TopicUtil.retrieveTopic(serviceTaskWorker), 5)
      .execute()
      .forEach { lockedTask ->
        val resultingVariables = Variables.createVariables()
        val writer = writer(resultingVariables) as VariableWriter<*>
        try {
          serviceTaskWorker.execute(reader = reader(lockedTask), writer = writer)
          externalTaskService.complete(lockedTask.id, workerId, resultingVariables)
        } catch (e: Exception) {
          when (e) {
            is BpmnError -> externalTaskService.handleBpmnError(lockedTask.id, workerId, e.errorCode, e.message)
            else -> externalTaskService.handleFailure(lockedTask.id, workerId, e.message, 1, 30)
          }
        }
      }
  }

  override fun postProcessAfterInitialization(bean: Any, beanName: String): Any {
    if (bean is ServiceTaskWorker && TopicUtil.hasTopic(bean)) {
      logger.info { "Found external task service: ${bean::class.java.canonicalName}, $beanName" }
      register(serviceTaskWorker = bean)
    }
    return bean
  }
}
