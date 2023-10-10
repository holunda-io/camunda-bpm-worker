package io.holunda.camunda.worker.impl

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * Properties to set up the Camunda 7 operation mode.
 */
@ConfigurationProperties("camunda.bpm.worker")
data class CamundaWorker7Properties(
  val taskMode: ExternalTaskMode
)
