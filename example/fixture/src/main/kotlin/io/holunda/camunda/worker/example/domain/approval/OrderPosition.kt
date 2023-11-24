package io.holunda.camunda.worker.example.domain.approval

import org.jmolecules.architecture.onion.classical.DomainModelRing
import java.math.BigDecimal

/**
 * Order position business entity.
 */
@DomainModelRing
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
