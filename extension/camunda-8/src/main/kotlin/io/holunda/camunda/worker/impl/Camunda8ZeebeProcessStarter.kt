package io.holunda.camunda.worker.impl

import io.camunda.zeebe.client.ZeebeClient
import io.holunda.camunda.worker.ProcessStartSummary
import io.holunda.camunda.worker.ProcessStarter

/**
 * Starter using C8 runtime to start process.
 */
class Camunda8ZeebeProcessStarter(
  private val zeebeClient: ZeebeClient
) : ProcessStarter {


  override fun startProcess(processKey: String, businessKey: String?, variables: Map<String, Any>): ProcessStartSummary {
    return zeebeClient
      .newCreateInstanceCommand()
      .bpmnProcessId(processKey)
      .latestVersion()
      .variables(variables)
      .send()
      .join()// FIXME -> join? and a unicorn dies?
      .let { event ->
        ProcessStartSummary(
          instanceId = event.processInstanceKey.toString(),
        )
      }
  }
}
