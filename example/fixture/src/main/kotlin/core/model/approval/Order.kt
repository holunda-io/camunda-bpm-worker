package io.holunda.camunda.worker.example.core.model.approval

import java.util.*


/**
 * Order business entity.
 */
data class Order(
  /**
   * Order id.
   */
  val orderId: OrderId,
  /**
   * Order create date.
   */
  val created: Date,
  /**
   * List of order positions.
   */
  val positions: List<OrderPosition> = listOf()
)
