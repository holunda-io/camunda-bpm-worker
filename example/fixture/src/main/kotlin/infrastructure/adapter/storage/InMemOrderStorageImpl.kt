package io.holunda.camunda.worker.example.infrastructure.adapter.storage

import io.holunda.camunda.worker.example.core.model.approval.Order
import io.holunda.camunda.worker.example.core.model.approval.OrderId
import io.holunda.camunda.worker.example.core.model.approval.OrderSubmission
import io.holunda.camunda.worker.example.core.model.calculation.OrderIdGenerator
import io.holunda.camunda.worker.example.core.port.out.storage.OrderStoragePort
import mu.KLogging
import org.jmolecules.architecture.onion.classical.InfrastructureRing
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.sql.Date
import java.time.Instant
import java.util.concurrent.ConcurrentHashMap

/**
 * Implementation of an order repository.
 */
@Component
@InfrastructureRing
class InMemOrderStorageImpl(
  private val orderIdGenerator: OrderIdGenerator
) : OrderStoragePort {

  companion object : KLogging()

  /**
   * Internal storage for order entities.
   */
  val store: MutableMap<String, OrderEntity> = listOf(
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
  ).associateBy { it.orderId }.toMutableMap()

  /**
   * Loads order by id.
   * @param orderId order id.
   * @return Order or null, if not found.
   */
  override fun loadOrder(orderId: OrderId): Order? {
    logger.info { "[ORDER Repository]: Requested to load order $orderId." }
    return store[orderId.value]?.toDomain().also {
      if (it != null) {
        logger.info { "[ORDER Repository]: Order found: $it" }
      } else {
        logger.info { "[ORDER Repository]: Order not found." }
      }
    }
  }

  /**
   * Stores order.
   * @param orderSubmission submission of a new order.
   * @return order id.
   */
  override fun storeOrder(orderSubmission: OrderSubmission): OrderId {
    val orderId = orderIdGenerator.generate(store.keys)
    return OrderId(orderId).also {
      store[orderId] = orderSubmission.toEntity(it)
    }
  }
}
