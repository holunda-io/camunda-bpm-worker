package io.holunda.camunda.worker.example.adapter.`in`.rest.approve

import io.holunda.camunda.worker.example.adapter.`in`.rest.OrderPositionDto
import java.util.*


/**
 * Order.
 */
data class OrderDto(
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
  val positions: List<OrderPositionDto> = listOf()
)
