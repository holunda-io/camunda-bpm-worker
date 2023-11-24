package io.holunda.camunda.worker.example.domain.approval

import org.jmolecules.architecture.onion.classical.DomainModelRing
import java.util.*

/**
 * Order used for submission.
 */
@DomainModelRing
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
