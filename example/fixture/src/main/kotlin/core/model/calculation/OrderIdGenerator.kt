package io.holunda.camunda.worker.example.core.model.calculation

import org.springframework.stereotype.Component

/**
 * Service for generating ids.
 */
@Component
class OrderIdGenerator {

  fun generate(keys: Set<String>): String {
    val orderId = keys.maxOfOrNull { it.toInt() }?.plus(1)
    return "$orderId"
  }
}
