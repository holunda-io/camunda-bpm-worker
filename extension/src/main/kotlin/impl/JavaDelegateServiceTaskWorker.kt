package io.holunda.camunda.worker.impl

import io.holunda.camunda.bpm.data.CamundaBpmData
import io.holunda.camunda.bpm.data.reader.VariableReader
import io.holunda.camunda.bpm.data.writer.VariableWriter
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate

abstract class JavaDelegateServiceTaskWorker : JavaDelegate {

  override fun execute(execution: DelegateExecution) {
    process(CamundaBpmData.reader(execution), CamundaBpmData.writer(execution))
  }

  abstract fun process(reader: VariableReader, writer: VariableWriter<*>)
}
