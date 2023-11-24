package io.holunda.camunda.worker.example.adapter.out.workflow

import io.holunda.camunda.bpm.data.reader.VariableReader
import io.holunda.camunda.bpm.data.writer.VariableWriter
import io.holunda.camunda.worker.ServiceTaskWorker
import io.holunda.camunda.worker.ServiceTypeAware
import io.holunda.camunda.worker.example.adapter.out.workflow.OrderApprovalProcess.ORDER
import io.holunda.camunda.worker.example.adapter.out.workflow.OrderApprovalProcess.ORDER_ID
import io.holunda.camunda.worker.example.adapter.out.workflow.OrderApprovalProcess.ORDER_TOTAL
import io.holunda.camunda.worker.example.application.port.out.OrderStoragePort
import io.holunda.camunda.worker.example.domain.approval.OrderId
import mu.KLogging
import org.jmolecules.architecture.hexagonal.SecondaryAdapter
import org.jmolecules.architecture.onion.classical.InfrastructureRing
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component("loadOrder")
@SecondaryAdapter
@InfrastructureRing
class LoadOrderWorker(
  val orderRepository: OrderStoragePort
) : ServiceTaskWorker, ServiceTypeAware {

  companion object : KLogging()

  override fun execute(reader: VariableReader, writer: VariableWriter<*>) {

    val orderId = reader.get(ORDER_ID)

    logger.info { "[LOAD ORDER]: Loading order by id: $orderId" }

    val order = orderRepository.loadOrder(OrderId(orderId))
    val total = BigDecimal.ZERO

    writer
      .set(ORDER_TOTAL, total)
      .set(ORDER, order)

    logger.info { "[LOAD ORDER]: Finished order loading, order is: $order, total is set to $total" }
  }

  override fun getServiceType(): String = "loadOrder"
}
