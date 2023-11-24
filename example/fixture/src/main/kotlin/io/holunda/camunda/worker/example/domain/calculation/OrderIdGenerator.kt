package io.holunda.camunda.worker.example.domain.calculation

import org.jmolecules.architecture.onion.classical.DomainServiceRing
import org.springframework.stereotype.Component

/**
 * Service for generating ids.
 */
@Component
@DomainServiceRing
class OrderIdGenerator {

  fun generate(keys: Set<String>): String {
    val orderId = keys.maxOfOrNull { it.toInt() }?.plus(1)
    return "$orderId"
  }
}
