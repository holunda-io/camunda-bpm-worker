package io.holunda.camunda.worker.example.adapter.`in`.rest

/**
 * Order position.
 */
data class OrderPositionDto(
  /**
   * Title.
   */
  val title: String,
  /**
   * Net cost per unit.
   */
  val netCost: String,
  /**
   * Amount (number of units).
   */
  val amount: Long
)
