package io.holunda.camunda.worker.example.infrastructure.adapter.rest.approve

/**
 * Approval decision DTO.
 */
data class ApprovalDecisionDto(
  /**
   * True if approved.
   */
  val approved: Boolean
)
