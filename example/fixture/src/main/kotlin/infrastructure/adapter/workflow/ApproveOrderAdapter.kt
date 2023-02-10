package io.holunda.camunda.worker.example.infrastructure.adapter.workflow

import io.holunda.camunda.bpm.data.CamundaBpmData.builder
import io.holunda.camunda.worker.UserTaskWorker
import io.holunda.camunda.worker.example.core.model.approval.ApprovalDecision
import io.holunda.camunda.worker.example.core.model.approval.Order
import io.holunda.camunda.worker.example.core.model.approval.OrderId
import io.holunda.camunda.worker.example.core.port.`in`.ApproveOrderPort
import io.holunda.camunda.worker.example.core.port.out.storage.OrderRepository
import io.holunda.camunda.worker.example.infrastructure.adapter.workflow.OrderApprovalProcess.Elements.TASK_APPROVE_ORDER
import io.holunda.camunda.worker.example.infrastructure.adapter.workflow.OrderApprovalProcess.ORDER_ID
import io.holunda.camunda.worker.io.holunda.camunda.worker.UserTaskQuery
import org.springframework.stereotype.Component

@Component
class ApproveOrderAdapter(
  private val userTaskWorker: UserTaskWorker,
  private val orderRepository: OrderRepository
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
