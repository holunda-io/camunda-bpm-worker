package io.holunda.camunda.worker.example.adapter.secondary.storage

import org.jmolecules.architecture.onion.classical.InfrastructureRing
import java.util.*


/**
 * Order entity used for persistence.
 */
@InfrastructureRing
class OrderEntity(
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
  val positions: List<OrderPositionValueObject> = listOf()
)
