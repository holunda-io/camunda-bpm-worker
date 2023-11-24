package io.holunda.camunda.worker.example.domain.approval

import org.jmolecules.architecture.onion.classical.DomainModelRing

/**
 * Order id.
 */
@JvmInline
@DomainModelRing
value class OrderId(val value: String)
