package io.holunda.camunda.worker.example.core.port.`in`

import io.holunda.camunda.worker.example.core.model.approval.OrderId
import io.holunda.camunda.worker.example.core.model.approval.OrderSubmission

/**
 * Starts approval.
 */
interface SubmitOrderInPort {
  /**
   * Starts the use case of order approval.
   */
  fun startApprovalOfExistingOrder(orderId: OrderId)

  /**
   * Submits a new order.
   */
  fun submitOrder(orderSubmission: OrderSubmission): OrderId
}
