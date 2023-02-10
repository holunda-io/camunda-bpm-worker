package io.holunda.camunda.worker.example.infrastructure.adapter.workflow

import io.holunda.camunda.bpm.data.CamundaBpmData
import io.holunda.camunda.worker.ProcessStarter
import io.holunda.camunda.worker.example.core.model.approval.OrderId
import io.holunda.camunda.worker.example.core.port.`in`.StartApprovalPort
import org.springframework.stereotype.Component

@Component
class StartApprovalAdapter(
  private val processStarter: ProcessStarter
) : StartApprovalPort {
  override fun startOrderApproval(orderId: OrderId) {
    processStarter.startProcess(
      processKey = OrderApprovalProcess.KEY,
      businessKey = orderId.value,
      CamundaBpmData.builder().set(OrderApprovalProcess.ORDER_ID, orderId.value).build()
    )
  }
}
