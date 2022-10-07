package io.holunda.camunda.worker.example.usecase

import io.holunda.camunda.worker.example.domain.model.ApprovalDecision
import io.holunda.camunda.worker.example.domain.model.Order

/**
 * Use case port.
 */
interface OrderApproval {

  /**
   * Starts the use case of order approval.
   */
  fun startOrderApproval(orderId: String)

  /**
   * Retrieves an order for approval.
   */
  fun getOrderForApproval(orderId: String): Order?

  /**
   * Decides about approval.
   */
  fun approveOrder(orderId: String, approvalDecision: ApprovalDecision)
}
