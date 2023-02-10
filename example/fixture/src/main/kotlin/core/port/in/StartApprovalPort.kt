package io.holunda.camunda.worker.example.core.port.`in`

import io.holunda.camunda.worker.example.core.model.approval.OrderId

/**
 * Starts approval.
 */
interface StartApprovalPort {
  /**
   * Starts the use case of order approval.
   */
  fun startOrderApproval(orderId: OrderId)
}
