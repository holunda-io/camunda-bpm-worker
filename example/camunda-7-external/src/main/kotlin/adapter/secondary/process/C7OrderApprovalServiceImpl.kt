package io.holunda.camunda.worker.example.adapter.secondary.process

import io.holunda.camunda.bpm.data.CamundaBpmData.builder
import io.holunda.camunda.worker.example.domain.model.ApprovalDecision
import io.holunda.camunda.worker.example.domain.model.Order
import io.holunda.camunda.worker.example.application.OrderApprovalProcess.Elements.TASK_APPROVE_ORDER
import io.holunda.camunda.worker.example.application.OrderApprovalProcess.KEY
import io.holunda.camunda.worker.example.application.OrderApprovalProcess.ORDER
import io.holunda.camunda.worker.example.application.OrderApprovalProcess.ORDER_APPROVED
import io.holunda.camunda.worker.example.application.OrderApprovalProcess.ORDER_ID
import io.holunda.camunda.worker.example.application.OrderApprovalPrimaryPort
import org.camunda.bpm.engine.ProcessEngineServices
import org.springframework.stereotype.Component

@Component
class C7OrderApprovalServiceImpl(
  val processEngineServices: ProcessEngineServices
) : OrderApprovalPrimaryPort {

  override fun startOrderApproval(orderId: String) {
    processEngineServices
      .runtimeService
      .startProcessInstanceByKey(KEY, orderId, builder().set(ORDER_ID, orderId).build())
  }

  override fun getOrderForApproval(orderId: String): Order? {
    return processEngineServices
      .runtimeService
      .createProcessInstanceQuery()
      .active()
      .processInstanceBusinessKey(orderId)
      .singleResult()?.let {
        ORDER.from(processEngineServices.runtimeService, it.id).get()
      }
  }

  override fun approveOrder(orderId: String, approvalDecision: ApprovalDecision) {
    processEngineServices
      .taskService
      .createTaskQuery()
      .active()
      .taskDefinitionKey(TASK_APPROVE_ORDER)
      .processVariableValueEquals(ORDER_ID.name, orderId)
      .singleResult()?.let {
        processEngineServices
          .taskService.complete(it.id, builder().set(ORDER_APPROVED, approvalDecision.approved).build())
      }
  }
}
