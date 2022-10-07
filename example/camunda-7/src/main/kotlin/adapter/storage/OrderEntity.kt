package io.holunda.camunda.worker.example.adapter.storage

import java.util.*


/**
 * Order business entity.
 */
class OrderEntity(
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
  val positions: List<OrderPositionValueObject> = listOf()
)
