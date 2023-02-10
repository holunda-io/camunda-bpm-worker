package io.holunda.camunda.worker.example.core.port.out.storage

import io.holunda.camunda.worker.example.core.model.approval.Order
import io.holunda.camunda.worker.example.core.model.approval.OrderId
import org.jmolecules.architecture.hexagonal.SecondaryPort

/**
 * Driven port for retrieval of orders.
 */
@SecondaryPort
interface OrderRepository {
  /**
   * Loads an order by id.
   * @param orderId order to load.
   * @return Order if found.
   */
  fun loadOrder(orderId: OrderId): Order?
}
