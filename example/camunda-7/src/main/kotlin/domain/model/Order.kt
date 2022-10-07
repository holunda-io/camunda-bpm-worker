package io.holunda.camunda.worker.example.domain.model

import java.util.*


/**
 * Order business entity.
 */
data class Order(
  /**
   * Order id.
   */
  val orderId: String,
  /**
   * Order create date.
   */
  val created: Date,
  /**
   * List of order positions.
   */
  val positions: List<OrderPosition> = listOf()
)
