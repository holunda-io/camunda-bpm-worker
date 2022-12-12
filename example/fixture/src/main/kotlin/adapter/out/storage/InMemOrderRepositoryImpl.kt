package io.holunda.camunda.worker.example.adapter.secondary.storage

import io.holunda.camunda.worker.example.domain.OrderRepository
import io.holunda.camunda.worker.example.domain.model.Order
import mu.KLogging
import org.jmolecules.architecture.onion.classical.InfrastructureRing
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.sql.Date
import java.time.Instant

/**
 * Implementation of an order repository.
 */
@Component
@InfrastructureRing
class InMemOrderRepositoryImpl : OrderRepository {

  companion object : KLogging()

  /**
   * Internal storage for order entities.
   */
  val store: Map<String, OrderEntity> = listOf(
    OrderEntity(
      orderId = "1",
      created = Date.from(Instant.now()),
      positions = listOf(
        OrderPositionValueObject(title = "Pencil", netCost = BigDecimal.valueOf(1.50), amount = 2),
        OrderPositionValueObject(title = "Pen", netCost = BigDecimal.valueOf(2.10), amount = 2)
      )
    ),
    OrderEntity(
      orderId = "2",
      created = Date.from(Instant.now()),
      positions = listOf(
        OrderPositionValueObject(title = "Milk", netCost = BigDecimal.valueOf(1.50), amount = 12),
        OrderPositionValueObject(title = "Eggs", netCost = BigDecimal.valueOf(3.10), amount = 24)
      )
    )
  ).associateBy { it.orderId }

  /**
   * Loads order by id.
   * @param orderId order id.
   * @return Order or null, if not found.
   */
  override fun loadOrder(orderId: String): Order? {
    logger.info { "[ORDER Repository]: Requested to load order $orderId." }
    return store[orderId]?.toDomain().also {
      if (it != null) {
        logger.info { "[ORDER Repository]: Order found: $it" }
      } else {
        logger.info { "[ORDER Repository]: Order not found." }
      }
    }
  }
}
