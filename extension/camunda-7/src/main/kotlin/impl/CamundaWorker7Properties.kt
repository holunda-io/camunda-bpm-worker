package io.holunda.camunda.worker.impl

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

/**
 * Properties to set up the Camunda 7 operation mode.
 */
@ConfigurationProperties("camunda.bpm.worker")
@ConstructorBinding
data class CamundaWorker7Properties(
  val taskMode: ExternalTaskMode
)

