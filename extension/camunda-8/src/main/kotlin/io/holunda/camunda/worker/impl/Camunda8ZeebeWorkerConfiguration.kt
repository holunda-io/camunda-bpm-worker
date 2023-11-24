package io.holunda.camunda.worker.impl

import com.fasterxml.jackson.databind.ObjectMapper
import io.camunda.zeebe.client.ZeebeClient
import io.camunda.zeebe.spring.client.exception.ZeebeBpmnError
import io.holunda.camunda.bpm.data.reader.MapReader
import io.holunda.camunda.bpm.data.writer.VariableMapWriter
import io.holunda.camunda.worker.ServiceTaskBpmnError
import org.camunda.bpm.engine.variable.Variables
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import java.util.*

class Camunda8ZeebeWorkerConfiguration(
  private val registry: ServiceTaskWorkerCamunda8Registry,
  private val zeebeClient: ZeebeClient,
  private val objectMapper: ObjectMapper
) {

  @EventListener(ApplicationReadyEvent::class)
  fun register() {
    connectWorkers()
  }

  private fun connectWorkers() {
    registry.registeredWorkers.forEach { serviceTaskWorker ->
      // FIXME -> handle the future processing of the zeebe client!
      zeebeClient
        .newWorker()
        .jobType(TopicUtil.retrieveServiceType(serviceTaskWorker))
        .handler { client, job ->
          ServiceTaskWorkerCamunda8Registry.logger.info { "Processing task ${job.key} of type ${job.type}" }
          val resultingGlobalVariables = Variables.createVariables()
          val writer = VariableMapWriter(variables = resultingGlobalVariables, delegateLocalToGlobal = true)
          val reader = MapReader(objectMapper = objectMapper, json = job.variablesAsMap, delegateLocalToGlobal = true)

          try {
            // this should ideally run in a transaction
            serviceTaskWorker.execute(reader = reader, writer = writer)
            client
              .newCompleteCommand(job.key)
              .variables(resultingGlobalVariables)
              .send()
              .join() // FIXME
          } catch (e: Exception) {
            ServiceTaskWorkerCamunda8Registry.logger.error(e) { "Error in worker: ${job.worker}" }
            when (e) {
              is ServiceTaskBpmnError -> client.newThrowErrorCommand(job.key).errorCode(e.errorCode).errorMessage(e.message).send().join()
              is ZeebeBpmnError -> client.newThrowErrorCommand(job.key).errorCode(e.errorCode).errorMessage(e.message).send().join()
              else -> client.newFailCommand(job.key).retries(1).errorMessage(e.message).send().join()
            }
          }

        }
        .name("${serviceTaskWorker.javaClass.canonicalName}-${UUID.randomUUID()}")
        .open()
    }
  }
}
