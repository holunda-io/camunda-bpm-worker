package io.holunda.camunda.worker.example.adapter.primary.delegate

import io.holunda.camunda.bpm.data.reader.VariableReader
import io.holunda.camunda.bpm.data.writer.VariableWriter
import io.holunda.camunda.worker.ServiceTaskWorker
import io.holunda.camunda.worker.ServiceType
import io.holunda.camunda.worker.example.application.OrderApprovalProcess.ORDER_POSITION
import io.holunda.camunda.worker.example.application.OrderApprovalProcess.ORDER_TOTAL
import io.holunda.camunda.worker.example.domain.model.OrderPosition
import mu.KLogging
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component("calculateOrderPositions")
@ServiceType("calculateOrderPositions")
class CalculateOrderPositionsWorker : ServiceTaskWorker {

  companion object: KLogging()

  override fun execute(reader: VariableReader, writer: VariableWriter<*>) {

    val orderPosition = reader.get(ORDER_POSITION)

    logger.info { "[CALCULATE ORDER TOTAL]: Starting calculation for order position: $orderPosition" }

    val total = reader.get(ORDER_TOTAL)
    val newTotal = calculate(total, orderPosition)
    writer.set(ORDER_TOTAL, newTotal)

    logger.info { "[CALCULATE ORDER TOTAL]: Finished calculation, writing total of $newTotal" }
  }

  private fun calculate(oldTotal: BigDecimal, orderPosition: OrderPosition): BigDecimal =
    oldTotal.plus(orderPosition.netCost.times(BigDecimal.valueOf(orderPosition.amount)))
}
