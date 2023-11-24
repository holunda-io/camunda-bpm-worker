package io.holunda.camunda.worker.example.application.port.`in`

import io.holunda.camunda.worker.example.domain.approval.ApprovalDecision
import io.holunda.camunda.worker.example.domain.approval.Order
import io.holunda.camunda.worker.example.domain.approval.OrderId
import org.jmolecules.architecture.hexagonal.PrimaryPort
import org.jmolecules.architecture.onion.classical.ApplicationServiceRing

/**
 * Use case of order approval.
 */
@PrimaryPort
@ApplicationServiceRing
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
