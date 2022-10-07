package io.holunda.camunda.worker.example.adapter.storage

import java.math.BigDecimal

/**
 * Order position business entity.
 */
data class OrderPositionValueObject(
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
