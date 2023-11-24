package io.holunda.camunda.worker.example.adapter.`in`.rest

import io.holunda.camunda.worker.example.adapter.`in`.rest.approve.ApprovalDecisionDto
import io.holunda.camunda.worker.example.adapter.`in`.rest.approve.OrderDto
import io.holunda.camunda.worker.example.adapter.`in`.rest.submit.OrderSubmissionDto
import io.holunda.camunda.worker.example.domain.approval.ApprovalDecision
import io.holunda.camunda.worker.example.domain.approval.Order
import io.holunda.camunda.worker.example.domain.approval.OrderPosition
import io.holunda.camunda.worker.example.domain.approval.OrderSubmission
import org.jmolecules.architecture.onion.classical.InfrastructureRing
import java.math.BigDecimal

@InfrastructureRing
object RestMappers {
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
}
