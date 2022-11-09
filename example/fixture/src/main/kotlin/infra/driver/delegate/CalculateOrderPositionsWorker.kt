package io.holunda.camunda.worker.example.infra.driver.delegate

import io.holunda.camunda.bpm.data.reader.VariableReader
import io.holunda.camunda.bpm.data.writer.VariableWriter
import io.holunda.camunda.worker.ServiceTaskWorker
import io.holunda.camunda.worker.Topic
import io.holunda.camunda.worker.example.application.OrderApprovalProcess.ORDER_POSITION
import io.holunda.camunda.worker.example.application.OrderApprovalProcess.ORDER_TOTAL
import io.holunda.camunda.worker.example.domain.model.OrderPosition
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component("calculateOrderPositions")
@Topic("calculateOrderPositions")
class CalculateOrderPositionsWorker : ServiceTaskWorker {

  override fun execute(reader: VariableReader, writer: VariableWriter<*>) {
    val orderPosition = reader.get(ORDER_POSITION)

    val total = reader.get(ORDER_TOTAL)
    writer.set(ORDER_TOTAL, calculate(total, orderPosition))

    // doesn't work with external tasks
    // writer.update(ORDER_TOTAL) { total -> calculate(total, orderPosition) }
  }

  private fun calculate(oldTotal: BigDecimal, orderPosition: OrderPosition): BigDecimal =
    oldTotal.plus(orderPosition.netCost.times(BigDecimal.valueOf(orderPosition.amount)))
}
