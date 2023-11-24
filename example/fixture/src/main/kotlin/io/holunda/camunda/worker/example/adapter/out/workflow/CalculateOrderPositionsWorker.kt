package io.holunda.camunda.worker.example.adapter.out.workflow

import io.holunda.camunda.bpm.data.reader.VariableReader
import io.holunda.camunda.bpm.data.writer.VariableWriter
import io.holunda.camunda.worker.ServiceTaskWorker
import io.holunda.camunda.worker.ServiceType
import io.holunda.camunda.worker.example.adapter.out.workflow.OrderApprovalProcess.ORDER_POSITION
import io.holunda.camunda.worker.example.adapter.out.workflow.OrderApprovalProcess.ORDER_TOTAL
import io.holunda.camunda.worker.example.domain.calculation.CalculationService
import mu.KLogging
import org.jmolecules.architecture.hexagonal.SecondaryAdapter
import org.jmolecules.architecture.onion.classical.InfrastructureRing
import org.springframework.stereotype.Component

@Component("calculateOrderPositions")
@ServiceType("calculateOrderPositions")
@SecondaryAdapter
@InfrastructureRing
class CalculateOrderPositionsWorker(
  val calculationService: CalculationService
) : ServiceTaskWorker {

  companion object : KLogging()

  override fun execute(reader: VariableReader, writer: VariableWriter<*>) {

    val orderPosition = reader.get(ORDER_POSITION)

    logger.info { "[CALCULATE ORDER TOTAL]: Starting calculation for order position: $orderPosition" }

    val total = reader.get(ORDER_TOTAL)
    val newTotal = calculationService.calculateTotal(total, orderPosition)
    writer.set(ORDER_TOTAL, newTotal)

    logger.info { "[CALCULATE ORDER TOTAL]: Finished calculation, writing total of $newTotal" }
  }

}
