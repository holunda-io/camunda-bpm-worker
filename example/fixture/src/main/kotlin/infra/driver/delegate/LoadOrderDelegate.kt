package io.holunda.camunda.worker.example.infra.driver.delegate

import io.holunda.camunda.bpm.data.reader.VariableReader
import io.holunda.camunda.bpm.data.writer.VariableWriter
import io.holunda.camunda.worker.example.application.OrderApprovalProcess.ORDER
import io.holunda.camunda.worker.example.application.OrderApprovalProcess.ORDER_ID
import io.holunda.camunda.worker.example.application.OrderApprovalProcess.ORDER_TOTAL
import io.holunda.camunda.worker.example.domain.service.OrderRepositoryPort
import io.holunda.camunda.worker.impl.JavaDelegateServiceTaskWorker
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component("loadOrder")
class LoadOrderDelegate(
  val orderRepository: OrderRepositoryPort
) : JavaDelegateServiceTaskWorker() {

  override fun process(reader: VariableReader, writer: VariableWriter<*>) {
    val orderId = reader.get(ORDER_ID)
    val order = orderRepository.loadOrder(orderId)

    writer
      .set(ORDER_TOTAL, BigDecimal.ZERO)
      .set(ORDER, order)
  }
}
