package io.holunda.camunda.worker.example.adapter.out.storage

import java.util.*


/**
 * Order entity used for persistence.
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
