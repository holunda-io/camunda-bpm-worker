package io.holunda.camunda.worker.example.domain.approval

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import org.jmolecules.architecture.onion.classical.DomainModelRing
import java.util.*


/**
 * Order business entity.
 */
@DomainModelRing
data class Order(
  /**
   * Order id.
   */
  val orderId: OrderId,
  /**
   * Order create date.
   */
  val created: Date,
  /**
   * List of order positions.
   */
  val positions: List<OrderPosition> = listOf()
)
