package io.holunda.camunda.worker.example.application.service

import io.holunda.camunda.bpm.data.CamundaBpmData.builder
import io.holunda.camunda.worker.UserTaskQuery
import io.holunda.camunda.worker.UserTaskWorker
import io.holunda.camunda.worker.example.application.port.`in`.ApproveOrderPort
import io.holunda.camunda.worker.example.application.port.out.OrderStoragePort
import io.holunda.camunda.worker.example.domain.approval.ApprovalDecision
import io.holunda.camunda.worker.example.domain.approval.Order
import io.holunda.camunda.worker.example.domain.approval.OrderId
import io.holunda.camunda.worker.example.adapter.out.workflow.OrderApprovalProcess
import io.holunda.camunda.worker.example.adapter.out.workflow.OrderApprovalProcess.Elements.TASK_APPROVE_ORDER
import io.holunda.camunda.worker.example.adapter.out.workflow.OrderApprovalProcess.ORDER_ID
import org.jmolecules.architecture.onion.classical.ApplicationServiceRing
import org.springframework.stereotype.Component

@Component
@ApplicationServiceRing
class ApproveOrderUseCase(
  private val userTaskWorker: UserTaskWorker,
  private val orderRepository: OrderStoragePort
) : ApproveOrderPort {

  override fun getOrderForApproval(orderId: OrderId): Order? {
    return loadTaskForOrderId(orderId)
      ?.let { task ->
        val taskOrderId = task.reader.get(ORDER_ID)
        orderRepository.loadOrder(OrderId(taskOrderId))
      }
  }

  override fun approveOrder(orderId: OrderId, approvalDecision: ApprovalDecision) {
    loadTaskForOrderId(orderId)
      ?.let {
        userTaskWorker.completeUserTask(
          taskId = it.taskId,
          userId = null,
          variables = builder().set(OrderApprovalProcess.ORDER_APPROVED, approvalDecision.approved).build()
        )
      }
  }

  private fun loadTaskForOrderId(orderId: OrderId) =
    userTaskWorker.loadUserTask(
      UserTaskQuery(
        businessKey = orderId.value,
        variables = builder().set(ORDER_ID, orderId.value).build(),
        type = TASK_APPROVE_ORDER
      )
    )
}
