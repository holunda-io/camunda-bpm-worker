package io.holunda.camunda.worker.example.core.application

import io.holunda.camunda.bpm.data.CamundaBpmData
import io.holunda.camunda.worker.ProcessStarter
import io.holunda.camunda.worker.example.core.model.approval.OrderId
import io.holunda.camunda.worker.example.core.model.approval.OrderSubmission
import io.holunda.camunda.worker.example.core.port.`in`.SubmitOrderInPort
import io.holunda.camunda.worker.example.core.port.out.storage.OrderStoragePort
import io.holunda.camunda.worker.example.infrastructure.adapter.workflow.OrderApprovalProcess
import org.springframework.stereotype.Component

@Component
class SubmitOrderUseCase(
  private val processStarter: ProcessStarter,
  private val orderStoragePort: OrderStoragePort

) : SubmitOrderInPort {

  override fun submitOrder(orderSubmission: OrderSubmission): OrderId {
    return orderStoragePort
      .storeOrder(orderSubmission)
      .also {
        processStarter.startProcess(
          processKey = OrderApprovalProcess.KEY,
          businessKey = it.value,
          CamundaBpmData.builder().set(OrderApprovalProcess.ORDER_ID, it.value).build()
        )
    }
  }

  override fun startApprovalOfExistingOrder(orderId: OrderId) {
    processStarter.startProcess(
      processKey = OrderApprovalProcess.KEY,
      businessKey = orderId.value,
      CamundaBpmData.builder().set(OrderApprovalProcess.ORDER_ID, orderId.value).build()
    )
  }
}
