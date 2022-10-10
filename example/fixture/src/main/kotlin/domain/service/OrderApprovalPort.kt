package io.holunda.camunda.worker.example.domain.service

import io.holunda.camunda.worker.example.domain.model.ApprovalDecision
import io.holunda.camunda.worker.example.domain.model.Order
import org.jmolecules.architecture.hexagonal.PrimaryPort

/**
 * Use case port.
 */
@PrimaryPort
interface OrderApprovalPort {

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
