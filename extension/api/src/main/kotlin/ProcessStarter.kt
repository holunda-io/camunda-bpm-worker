package io.holunda.camunda.worker

/**
 * Start processes.
 */
interface ProcessStarter {

  /**
   * Starts a business process.
   * @param processKey from the modeler.
   * @param businessKey optional business key.
   * @param variables process variables to start the process with.
   * @return process start summary.
   */
  fun startProcess(processKey: String, businessKey: String?, variables: Map<String, Any>): ProcessStartSummary
}
