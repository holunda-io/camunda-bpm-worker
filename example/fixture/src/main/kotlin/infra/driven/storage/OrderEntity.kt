package io.holunda.camunda.worker.example.infra.driven.storage

import org.jmolecules.architecture.onion.classical.InfrastructureRing
import java.util.*


/**
 * Order business entity.
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
