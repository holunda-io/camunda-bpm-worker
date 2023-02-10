package io.holunda.camunda.worker.example.core.port.`in`

import io.holunda.camunda.worker.example.core.model.approval.ApprovalDecision
import io.holunda.camunda.worker.example.core.model.approval.Order
import io.holunda.camunda.worker.example.core.model.approval.OrderId
import org.jmolecules.architecture.hexagonal.PrimaryPort

/**
 * Use case of order approval.
 */
@PrimaryPort
interface ApproveOrderPort {

  /**
   * Retrieves an order for approval.
   */
  fun getOrderForApproval(orderId: OrderId): Order?

  /**
   * Decides about approval.
   */
  fun approveOrder(orderId: OrderId, approvalDecision: ApprovalDecision)
}
