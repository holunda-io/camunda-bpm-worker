package io.holunda.camunda.worker.example.application

import io.holunda.camunda.worker.example.domain.model.ApprovalDecision
import io.holunda.camunda.worker.example.domain.model.Order
import org.jmolecules.architecture.hexagonal.PrimaryPort

/**
 * Application port. Will be used by invoked by the driving adapter.
 */
@PrimaryPort
interface OrderApprovalPrimaryPort {

  /**
   * Starts the use case of order approval.
   * @param orderId id of the order to approve.
   */
  fun startOrderApproval(orderId: String)

  /**
   * Retrieves an order for approval.
   * @param orderId id of the order to load.
   * @return order found by id.
   */
  fun getOrderForApproval(orderId: String): Order?

  /**
   * Decides about approval.
   * @param orderId order to decide about.
   * @param approvalDecision decision about the order.
   */
  fun approveOrder(orderId: String, approvalDecision: ApprovalDecision)
}
