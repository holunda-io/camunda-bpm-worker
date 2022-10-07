package io.holunda.camunda.worker.example.adapter.rest

import io.holunda.camunda.worker.example.domain.model.ApprovalDecision
import io.holunda.camunda.worker.example.domain.model.Order
import io.holunda.camunda.worker.example.domain.model.OrderPosition

fun Order.toDto() = OrderDto(
  orderId = this.orderId,
  created = this.created,
  positions = this.positions.map { position -> position.toDto() }
)

fun OrderPosition.toDto() = OrderPositionDto(
  title = this.title,
  netCost = this.netCost.toString(),
  amount = this.amount
)

fun ApprovalDecisionDto.toDomain() = ApprovalDecision(this.approved)

