package io.holunda.camunda.worker.example.infrastructure.adapter.rest.submit

import io.holunda.camunda.worker.example.infrastructure.adapter.rest.OrderPositionDto
import java.util.*

data class OrderSubmissionDto(
  /**
   * Order create date.
   */
  val created: Date,
  /**
   * List of order positions.
   */
  val positions: List<OrderPositionDto> = listOf()
)
