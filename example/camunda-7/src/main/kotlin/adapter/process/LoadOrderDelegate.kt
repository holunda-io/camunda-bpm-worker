package io.holunda.camunda.worker.example.adapter.process

import io.holunda.camunda.bpm.data.reader.VariableReader
import io.holunda.camunda.bpm.data.writer.VariableWriter
import io.holunda.camunda.worker.example.domain.process.OrderApprovalProcess.ORDER
import io.holunda.camunda.worker.example.domain.process.OrderApprovalProcess.ORDER_ID
import io.holunda.camunda.worker.example.domain.process.OrderApprovalProcess.ORDER_TOTAL
import io.holunda.camunda.worker.example.domain.service.OrderRepository
import io.holunda.camunda.worker.impl.JavaDelegateServiceTaskWorker
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component("loadOrder")
class LoadOrderDelegate(
  val orderRepository: OrderRepository
) : JavaDelegateServiceTaskWorker() {

  override fun process(reader: VariableReader, writer: VariableWriter<*>) {
    val orderId = reader.get(ORDER_ID)
    val order = orderRepository.loadOrder(orderId)

    writer
      .set(ORDER_TOTAL, BigDecimal.ZERO)
      .set(ORDER, order)
  }
}
