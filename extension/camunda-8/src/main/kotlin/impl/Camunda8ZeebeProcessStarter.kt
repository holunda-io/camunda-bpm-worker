package io.holunda.camunda.worker.impl

import io.camunda.zeebe.client.ZeebeClient
import io.holunda.camunda.worker.ProcessStartSummary
import io.holunda.camunda.worker.ProcessStarter

class Camunda8ZeebeProcessStarter(
  private val zeebeClient: ZeebeClient
) : ProcessStarter {


  override fun startProcess(processKey: String, businessKey: String?, variables: Map<String, Any>): ProcessStartSummary {
    return zeebeClient
      .newCreateInstanceCommand()
      .bpmnProcessId(processKey)
      .latestVersion()
      .variables(variables)
      .send().join().let { event ->
        ProcessStartSummary(
          instanceId = event.processInstanceKey.toString(),
        )
      }
  }
}