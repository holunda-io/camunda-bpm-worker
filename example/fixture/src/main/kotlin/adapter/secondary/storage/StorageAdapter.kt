@file:InfrastructureRing
package io.holunda.camunda.worker.example.adapter.secondary.storage

import org.jmolecules.architecture.onion.classical.InfrastructureRing
import io.holunda.camunda.worker.example.domain.model.Order
import io.holunda.camunda.worker.example.domain.model.OrderPosition

fun OrderEntity.toDomain() = Order(
  orderId = this.orderId,
  created = this.created,
  positions = this.positions.map { position -> position.toDomain() }
)

fun OrderPositionValueObject.toDomain() = OrderPosition(
  title = this.title,
  netCost = this.netCost,
  amount = this.amount
)

