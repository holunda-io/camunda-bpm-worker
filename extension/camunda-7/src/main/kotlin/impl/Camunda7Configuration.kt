package io.holunda.camunda.worker.impl

import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Import

@AutoConfiguration
@Import(
  value = [
    ServiceTaskWorkerCamunda7Registry::class,
    Camunda7ExternalTaskClientConfiguration::class,
    Camunda7ExternalTaskServiceConfiguration::class,
  ]
)
@EnableConfigurationProperties(value = [CamundaWorker7Properties::class])
class Camunda7Configuration
