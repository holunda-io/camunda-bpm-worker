package io.holunda.camunda.worker.example.infra.driver.delegate

import io.holunda.camunda.bpm.data.reader.VariableReader
import io.holunda.camunda.bpm.data.writer.VariableWriter
import io.holunda.camunda.worker.ServiceTaskWorker
import io.holunda.camunda.worker.Topic
import io.holunda.camunda.worker.example.application.OrderApprovalProcess.ORDER_POSITION
import io.holunda.camunda.worker.example.application.OrderApprovalProcess.ORDER_TOTAL
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component("calculateOrderPositions")
@Topic("calculateOrderPositions")
class CalculateOrderPositionsWorker : ServiceTaskWorker {

  override fun execute(reader: VariableReader, writer: VariableWriter<*>) {
    val orderPosition = reader.get(ORDER_POSITION)
    writer.update(ORDER_TOTAL) { total -> total.plus(orderPosition.netCost.times(BigDecimal.valueOf(orderPosition.amount))) }
  }
}
