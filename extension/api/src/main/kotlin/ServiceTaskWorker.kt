package io.holunda.camunda.worker

import io.holunda.camunda.bpm.data.reader.VariableReader
import io.holunda.camunda.bpm.data.writer.VariableWriter

/**
 * Abstraction of a service worker.
 */
interface ServiceTaskWorker {
  /**
   * Executes the service task.
   * @param reader a variable reader to get variables.
   * @param writer a variable writer to write variables back.
   * @throws [ServiceTaskBpmnError] if you want to propagate a BPMN error.
   */
  @Throws(ServiceTaskBpmnError::class)
  fun execute(reader: VariableReader, writer: VariableWriter<*>)
}
