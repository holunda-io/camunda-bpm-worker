package io.holunda.camunda.worker

import io.holunda.camunda.bpm.data.reader.VariableReader
import io.holunda.camunda.bpm.data.writer.VariableWriter

/**
 * Abstraction of a service worker.
 */
interface ServiceTaskWorker {
  fun execute(reader: VariableReader, writer: VariableWriter<*>)
}
