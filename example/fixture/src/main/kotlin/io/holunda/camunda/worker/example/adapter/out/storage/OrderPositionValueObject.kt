package io.holunda.camunda.worker.example.adapter.out.storage

import org.jmolecules.architecture.onion.classical.InfrastructureRing
import java.math.BigDecimal

/**
 * Order position used for persistence.
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
