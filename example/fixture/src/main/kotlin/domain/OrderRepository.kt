package io.holunda.camunda.worker.example.domain

import io.holunda.camunda.worker.example.domain.model.Order
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
  fun loadOrder(orderId: String): Order?
}
