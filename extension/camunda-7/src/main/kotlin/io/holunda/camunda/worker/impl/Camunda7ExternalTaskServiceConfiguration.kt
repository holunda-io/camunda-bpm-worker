package io.holunda.camunda.worker.impl

import io.holunda.camunda.bpm.data.CamundaBpmData
import io.holunda.camunda.bpm.data.writer.TwoVariableMapsWriter
import io.holunda.camunda.worker.ServiceTaskBpmnError
import mu.KLogging
import org.camunda.bpm.engine.ExternalTaskService
import org.camunda.bpm.engine.delegate.BpmnError
import org.camunda.bpm.engine.variable.Variables
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import java.util.*


/**
 * Configuration registering worker from the registry using Camunda 7 External Task Service via Java API
 */
@EnableScheduling
@ConditionalOnProperty("camunda.bpm.worker.task-mode", havingValue = "SERVICE")
class Camunda7ExternalTaskServiceConfiguration(
  private val registry: ServiceTaskWorkerCamunda7Registry,
  private val externalTaskService: ExternalTaskService
) {
  companion object : KLogging()

  private val workers: List<Worker> by lazy {
    registry.registeredWorkers.map { serviceTaskWorker ->
      {
        val workerId = "${serviceTaskWorker.javaClass.canonicalName}-${UUID.randomUUID()}"
        externalTaskService
          .fetchAndLock(10, workerId)
          .topic(TopicUtil.retrieveServiceType(serviceTaskWorker), 5)
          .enableCustomObjectDeserialization()
          .execute()
          .forEach { lockedTask ->
            logger.info { "Processing external task ${lockedTask.id}" }
            val resultingGlobalVariables = Variables.createVariables()
            val resultingLocalVariables = Variables.createVariables()
            val writer = TwoVariableMapsWriter(global = resultingGlobalVariables, local = resultingLocalVariables)
            try {
              serviceTaskWorker.execute(reader = CamundaBpmData.reader(lockedTask), writer = writer)
              externalTaskService.complete(lockedTask.id, workerId, resultingGlobalVariables, resultingLocalVariables)
            } catch (e: Exception) {
              logger.error(e) { "Error external in worker: $workerId" }
              when (e) {
                is BpmnError -> externalTaskService.handleBpmnError(lockedTask.id, workerId, e.errorCode, e.message)
                is ServiceTaskBpmnError -> externalTaskService.handleBpmnError(lockedTask.id, workerId, e.errorCode, e.message)
                else -> externalTaskService.handleFailure(lockedTask.id, workerId, e.message, 1, 30)
              }
            }
          }
      }
    }
  }

  @Scheduled(initialDelayString = "PT15S", fixedDelayString = "PT5S")
  fun connectAndRunWorker() {
    workers.forEach { it.invoke() }
  }

}

typealias Worker = () -> Unit
