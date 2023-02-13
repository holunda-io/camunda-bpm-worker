package io.holunda.camunda.worker.example.core.model.approval

import java.util.*

/**
 * Order used for submission.
 */
data class OrderSubmission(
  /**
   * Order create date.
   */
  val created: Date,
  /**
   * List of order positions.
   */
  val positions: List<OrderPosition> = listOf()
)
