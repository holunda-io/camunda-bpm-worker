package io.holunda.camunda.worker.example.adapter.primary.rest

import io.holunda.camunda.worker.example.domain.model.ApprovalDecision
import io.holunda.camunda.worker.example.domain.model.Order
import io.holunda.camunda.worker.example.domain.model.OrderPosition

/**
 * Maps order domain representation to DTO.
 */
fun Order.toDto() = OrderDto(
  orderId = this.orderId,
  created = this.created,
  positions = this.positions.map { position -> position.toDto() }
)

/**
 * Maps order position domain representation to DTO.
 */
fun OrderPosition.toDto() = OrderPositionDto(
  title = this.title,
  netCost = this.netCost.toString(),
  amount = this.amount
)

/**
 * Maps DTO of approval decision to domain object.
 */
fun ApprovalDecisionDto.toDomain() = ApprovalDecision(this.approved)

