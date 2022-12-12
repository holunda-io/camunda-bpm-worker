package io.holunda.camunda.worker.example.adapter.secondary.process

import io.holunda.camunda.bpm.data.CamundaBpmData
import io.holunda.camunda.worker.ProcessStarter
import io.holunda.camunda.worker.example.application.OrderApprovalPort
import io.holunda.camunda.worker.example.application.OrderApprovalProcess
import io.holunda.camunda.worker.example.domain.model.ApprovalDecision
import io.holunda.camunda.worker.example.domain.model.Order

class OrderApprovalAdapterImpl(
  private val processStarter: ProcessStarter
): OrderApprovalPort {
  override fun startOrderApproval(orderId: String) {
    val variables: Map<String, Any> = CamundaBpmData
      .builder()
      .set(OrderApprovalProcess.ORDER_ID, orderId)
      .build()

    processStarter
      .startProcess(
        OrderApprovalProcess.KEY,
        null,
        variables
      )
  }

  override fun getOrderForApproval(orderId: String): Order? {
    TODO("Not yet implemented")
  }

  override fun approveOrder(orderId: String, approvalDecision: ApprovalDecision) {
    TODO("Not yet implemented")
  }
}
