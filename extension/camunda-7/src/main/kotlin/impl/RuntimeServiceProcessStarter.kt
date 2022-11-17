package impl

import io.holunda.camunda.worker.ProcessStartSummary
import io.holunda.camunda.worker.ProcessStarter
import org.camunda.bpm.engine.RuntimeService

class RuntimeServiceProcessStarter(
  private val runtimeService: RuntimeService
): ProcessStarter {

  override fun startProcess(processKey: String, businessKey: String?, variables: Map<String, Any>): ProcessStartSummary {
    runtimeService
  }
}