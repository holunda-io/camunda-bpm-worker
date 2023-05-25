@file:InfrastructureRing

package io.holunda.camunda.worker.example.infrastructure.adapter.storage

import io.holunda.camunda.worker.example.core.model.approval.Order
import io.holunda.camunda.worker.example.core.model.approval.OrderId
import io.holunda.camunda.worker.example.core.model.approval.OrderPosition
import io.holunda.camunda.worker.example.core.model.approval.OrderSubmission
import org.jmolecules.architecture.onion.classical.InfrastructureRing

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

fun OrderSubmission.toEntity(orderId: OrderId) = OrderEntity(
  orderId = orderId.value,
  created = this.created,
  positions = this.positions.map { it.toEntity() }
)

fun OrderPosition.toEntity() = OrderPositionValueObject(
  title = this.title,
  netCost = this.netCost,
  amount = this.amount
)

