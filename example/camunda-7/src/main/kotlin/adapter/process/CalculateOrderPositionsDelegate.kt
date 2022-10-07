package io.holunda.camunda.worker.example.adapter.process

import io.holunda.camunda.bpm.data.reader.VariableReader
import io.holunda.camunda.bpm.data.writer.VariableWriter
import io.holunda.camunda.worker.example.domain.process.OrderApprovalProcess.ORDER_POSITION
import io.holunda.camunda.worker.example.domain.process.OrderApprovalProcess.ORDER_TOTAL
import io.holunda.camunda.worker.impl.JavaDelegateServiceTaskWorker
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component("calculateOrderPositions")
class CalculateOrderPositionsDelegate : JavaDelegateServiceTaskWorker() {

  override fun process(reader: VariableReader, writer: VariableWriter<*>) {
    val orderPosition = reader.get(ORDER_POSITION)
    writer.update(ORDER_TOTAL) { total -> total.plus(orderPosition.netCost.times(BigDecimal.valueOf(orderPosition.amount))) }
  }
}
