package io.holunda.camunda.worker.example.application.port.out

import io.holunda.camunda.worker.example.domain.approval.Order
import io.holunda.camunda.worker.example.domain.approval.OrderId
import io.holunda.camunda.worker.example.domain.approval.OrderSubmission
import org.jmolecules.architecture.hexagonal.SecondaryPort

/**
 * Driven port for retrieval of orders.
 */
@SecondaryPort
interface OrderStoragePort {
  /**
   * Loads an order by id.
   * @param orderId order to load.
   * @return Order if found.
   */
  fun loadOrder(orderId: OrderId): Order?

  /**
   * Stores a submitted order.
   * @param orderSubmission order data to store.
   * @return order id of stored order.
   */
  fun storeOrder(orderSubmission: OrderSubmission): OrderId
}
