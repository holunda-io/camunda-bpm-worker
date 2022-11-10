package io.holunda.camunda.worker.example.adapter.primary.delegate

import io.holunda.camunda.bpm.data.reader.VariableReader
import io.holunda.camunda.bpm.data.writer.VariableWriter
import io.holunda.camunda.worker.ServiceTaskWorker
import io.holunda.camunda.worker.TopicAware
import io.holunda.camunda.worker.example.application.OrderApprovalProcess.ORDER
import io.holunda.camunda.worker.example.application.OrderApprovalProcess.ORDER_ID
import io.holunda.camunda.worker.example.application.OrderApprovalProcess.ORDER_TOTAL
import io.holunda.camunda.worker.example.domain.OrderRepositorySecondaryPort
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component("loadOrder")
class LoadOrderWorker(
  val orderRepository: OrderRepositorySecondaryPort
) : ServiceTaskWorker, TopicAware {

  override fun execute(reader: VariableReader, writer: VariableWriter<*>) {
    val orderId = reader.get(ORDER_ID)
    val order = orderRepository.loadOrder(orderId)

    writer
      .set(ORDER_TOTAL, BigDecimal.ZERO)
      .set(ORDER, order)
  }

  override fun getTopic(): String = "loadOrder"
}
