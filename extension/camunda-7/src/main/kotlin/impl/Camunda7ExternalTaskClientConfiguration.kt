package io.holunda.camunda.worker.impl

import io.holunda.camunda.bpm.data.CamundaBpmData
import io.holunda.camunda.worker.ServiceTaskBpmnError
import mu.KLogging
import org.camunda.bpm.client.ExternalTaskClient
import org.camunda.bpm.engine.delegate.BpmnError
import org.camunda.bpm.engine.variable.Variables
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Scheduled


@ConditionalOnProperty("camunda.bpm.worker.task-mode", havingValue = "CLIENT")
class Camunda7ExternalTaskClientConfiguration(
  private val registry: ServiceTaskWorkerCamunda7Registry,
  private val externalTaskClient: ExternalTaskClient
) {

  companion object: KLogging()

  @EventListener(ApplicationReadyEvent::class)
  fun register() {
    connect(externalTaskClient)
  }

  fun connect(externalTaskClient: ExternalTaskClient) {
    registry.registeredWorkers.forEach { serviceTaskWorker ->
      externalTaskClient
        .subscribe(TopicUtil.retrieveServiceType(serviceTaskWorker))
        .handler { externalTask, externalTaskService ->
          logger.info { "Processing external task ${externalTask.id}" }
          val reader = CamundaBpmData.reader(externalTask.allVariablesTyped)
          val resultingGlobalVariables = Variables.createVariables()
          val resultingLocalVariables = Variables.createVariables()
          val writer = TwoMapWriter(global = resultingGlobalVariables, local = resultingLocalVariables)
          try {
            serviceTaskWorker.execute(reader = reader, writer = writer)
            externalTaskService.complete(externalTask, resultingGlobalVariables, resultingLocalVariables)
          } catch (e: Exception) {
            logger.error(e) { "Error in external worker: ${externalTask.workerId}" }
            when (e) {
              is BpmnError -> externalTaskService.handleBpmnError(externalTask, e.errorCode, e.message)
              is ServiceTaskBpmnError -> externalTaskService.handleBpmnError(externalTask, e.errorCode, e.message)
              else -> externalTaskService.handleFailure(externalTask.id, e.message, "Error processing external task for topic ${externalTask.topicName}", 1, 30)
            }
          }
        }
        .open()
    }
  }
}