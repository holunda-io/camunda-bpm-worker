package io.holunda.camunda.worker.example.infra.driver.rest

import java.math.BigDecimal

/**
 * Order position business entity.
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
