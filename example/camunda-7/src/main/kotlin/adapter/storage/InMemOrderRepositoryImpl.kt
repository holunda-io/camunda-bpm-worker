package io.holunda.camunda.worker.example.adapter.storage

import io.holunda.camunda.worker.example.domain.model.Order
import io.holunda.camunda.worker.example.domain.service.OrderRepository
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.sql.Date
import java.time.Instant

@Component
class InMemOrderRepositoryImpl : OrderRepository {

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

  override fun loadOrder(orderId: String): Order? {
    return store[orderId]?.toDomain()
  }
}
