package io.holunda.camunda.worker.impl

import io.holunda.camunda.worker.ProcessStartSummary
import io.holunda.camunda.worker.ProcessStarter
import org.camunda.bpm.engine.RuntimeService

/**
 * Starter using C7 runtime service for process starts
 */
class RuntimeServiceProcessStarter(
  private val runtimeService: RuntimeService
): ProcessStarter {

  override fun startProcess(processKey: String, businessKey: String?, variables: Map<String, Any>): ProcessStartSummary {
    return ProcessStartSummary(
      runtimeService
        .startProcessInstanceByKey(processKey, businessKey, variables).id
    )
  }
}
