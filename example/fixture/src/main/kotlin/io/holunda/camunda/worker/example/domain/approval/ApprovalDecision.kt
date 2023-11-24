package io.holunda.camunda.worker.example.domain.approval

import org.jmolecules.architecture.onion.classical.DomainModelRing

/**
 * Represents the approval decision.
 */
@DomainModelRing
data class ApprovalDecision(
  val approved: Boolean
)
