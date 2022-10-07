package io.holunda.camunda.worker.example.domain.service

import io.holunda.camunda.worker.example.domain.model.Order

/**
 * Driven port for storage of orders.
 */
interface OrderRepository {
  /**
   * Loads an order by id.
   */
  fun loadOrder(orderId: String): Order?
}
