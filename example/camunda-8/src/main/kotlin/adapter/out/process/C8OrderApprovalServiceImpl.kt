package io.holunda.camunda.worker.example.adapter.secondary.process

import impl.TaskAccessor
import io.camunda.zeebe.client.ZeebeClient
import io.holunda.camunda.bpm.data.CamundaBpmData.builder
import io.holunda.camunda.worker.example.application.OrderApprovalPort
import io.holunda.camunda.worker.example.application.OrderApprovalProcess.Elements.TASK_APPROVE_ORDER
import io.holunda.camunda.worker.example.application.OrderApprovalProcess.KEY
import io.holunda.camunda.worker.example.application.OrderApprovalProcess.ORDER
import io.holunda.camunda.worker.example.application.OrderApprovalProcess.ORDER_APPROVED
import io.holunda.camunda.worker.example.application.OrderApprovalProcess.ORDER_ID
import io.holunda.camunda.worker.example.domain.model.ApprovalDecision
import io.holunda.camunda.worker.example.domain.model.Order
import org.springframework.stereotype.Component

// FIXME -> Think of how to integrate with the user tasks, write a connector?
@Component
class C8OrderApprovalServiceImpl(
  val zeebeClient: ZeebeClient,
  val taskAccessor: TaskAccessor
) : OrderApprovalPort {

  override fun startOrderApproval(orderId: String) {
    zeebeClient
      .newCreateInstanceCommand()
      .bpmnProcessId(KEY)
      .latestVersion()
      .variables(builder().set(ORDER_ID, orderId).build())
      .send()
  }

  override fun getOrderForApproval(orderId: String): Order? {
/*
    return processEngineServices
      .runtimeService
      .createProcessInstanceQuery()
      .active()
      .processInstanceBusinessKey(orderId)
      .singleResult()?.let {
        ORDER.from(processEngineServices.runtimeService, it.id).get()
      }
 */
    TODO()
  }

  override fun approveOrder(orderId: String, approvalDecision: ApprovalDecision) {
    /*
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
     */
    TODO()
  }
}
