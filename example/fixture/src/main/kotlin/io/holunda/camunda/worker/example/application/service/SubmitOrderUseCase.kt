package io.holunda.camunda.worker.example.application.service

import io.holunda.camunda.bpm.data.CamundaBpmData
import io.holunda.camunda.worker.ProcessStarter
import io.holunda.camunda.worker.example.application.port.`in`.SubmitOrderInPort
import io.holunda.camunda.worker.example.application.port.out.OrderStoragePort
import io.holunda.camunda.worker.example.domain.approval.OrderId
import io.holunda.camunda.worker.example.domain.approval.OrderSubmission
import io.holunda.camunda.worker.example.adapter.out.workflow.OrderApprovalProcess
import org.jmolecules.architecture.onion.classical.ApplicationServiceRing
import org.springframework.stereotype.Component

@Component
@ApplicationServiceRing
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
