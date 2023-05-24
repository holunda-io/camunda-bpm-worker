package io.holunda.camunda.worker.example.infrastructure.adapter.rest

import io.holunda.camunda.worker.example.core.model.approval.ApprovalDecision
import io.holunda.camunda.worker.example.core.model.approval.Order
import io.holunda.camunda.worker.example.core.model.approval.OrderPosition
import io.holunda.camunda.worker.example.core.model.approval.OrderSubmission
import io.holunda.camunda.worker.example.infrastructure.adapter.rest.approve.ApprovalDecisionDto
import io.holunda.camunda.worker.example.infrastructure.adapter.rest.approve.OrderDto
import io.holunda.camunda.worker.example.infrastructure.adapter.rest.submit.OrderSubmissionDto
import java.math.BigDecimal

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
fun ApprovalDecisionDto.toDomain() = ApprovalDecision(
  approved = this.approved
)

/**
 * Maps DTO to domain object.
 */
fun OrderSubmissionDto.toDomain() = OrderSubmission(
  created = this.created,
  positions = this.positions.map { it.toDomain() }
)

/**
 * Maps DTO to domain object.
 */
fun OrderPositionDto.toDomain() = OrderPosition(
  title = this.title,
  netCost = BigDecimal(netCost),
  amount = this.amount
)
