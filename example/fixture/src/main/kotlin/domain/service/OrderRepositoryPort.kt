package io.holunda.camunda.worker.example.domain.service

import io.holunda.camunda.worker.example.domain.model.Order
import org.jmolecules.architecture.hexagonal.SecondaryPort

/**
 * Driven port for storage of orders.
 */
@SecondaryPort
interface OrderRepositoryPort {
  /**
   * Loads an order by id.
   */
  fun loadOrder(orderId: String): Order?
}
