package io.holunda.camunda.worker.example.application.port.`in`

import io.holunda.camunda.worker.example.domain.approval.OrderId
import io.holunda.camunda.worker.example.domain.approval.OrderSubmission
import org.jmolecules.architecture.hexagonal.PrimaryPort
import org.jmolecules.architecture.onion.classical.ApplicationServiceRing

/**
 * Starts approval.
 */
@PrimaryPort
@ApplicationServiceRing
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
