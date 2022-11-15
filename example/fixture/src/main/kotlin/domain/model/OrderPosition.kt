package io.holunda.camunda.worker.example.domain.model

import java.math.BigDecimal

/**
 * Order position business entity.
 */
data class OrderPosition(
  /**
   * Title.
   */
  val title: String,
  /**
   * Net cost per unit.
   */
  val netCost: BigDecimal,
  /**
   * Amount (number of units).
   */
  val amount: Long
)
