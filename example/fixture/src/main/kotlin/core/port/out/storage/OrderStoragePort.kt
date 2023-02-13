package io.holunda.camunda.worker.example.core.port.out.storage

import io.holunda.camunda.worker.example.core.model.approval.Order
import io.holunda.camunda.worker.example.core.model.approval.OrderId
import io.holunda.camunda.worker.example.core.model.approval.OrderSubmission
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
