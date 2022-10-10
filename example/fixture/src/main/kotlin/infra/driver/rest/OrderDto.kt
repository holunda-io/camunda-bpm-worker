package io.holunda.camunda.worker.example.infra.driver.rest

import java.util.*


/**
 * Order business entity.
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
