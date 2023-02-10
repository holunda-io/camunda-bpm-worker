package io.holunda.camunda.worker.example.infrastructure.adapter.workflow

import io.holunda.camunda.bpm.data.reader.VariableReader
import io.holunda.camunda.bpm.data.writer.VariableWriter
import io.holunda.camunda.worker.ServiceTaskWorker
import io.holunda.camunda.worker.ServiceTypeAware
import io.holunda.camunda.worker.example.core.model.approval.OrderId
import io.holunda.camunda.worker.example.core.port.out.storage.OrderRepository
import io.holunda.camunda.worker.example.infrastructure.adapter.workflow.OrderApprovalProcess.ORDER
import io.holunda.camunda.worker.example.infrastructure.adapter.workflow.OrderApprovalProcess.ORDER_ID
import io.holunda.camunda.worker.example.infrastructure.adapter.workflow.OrderApprovalProcess.ORDER_TOTAL
import mu.KLogging
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component("loadOrder")
class LoadOrderWorker(
  val orderRepository: OrderRepository
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
