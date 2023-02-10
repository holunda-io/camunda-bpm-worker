package io.holunda.camunda.worker.example.infrastructure.adapter.rest

import io.holunda.camunda.worker.example.core.model.approval.ApprovalDecision
import io.holunda.camunda.worker.example.core.model.approval.Order
import io.holunda.camunda.worker.example.core.model.approval.OrderPosition

/**
 * Maps order domain representation to DTO.
 */
fun Order.toDto() = OrderDto(
  orderId = this.orderId.value,
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
fun ApprovalDecisionDto.toDomain() = ApprovalDecision(
  approved = this.approved
)

