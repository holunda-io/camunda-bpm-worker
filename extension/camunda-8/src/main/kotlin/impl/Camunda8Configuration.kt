package io.holunda.camunda.worker.impl

import io.camunda.zeebe.spring.client.ZeebeClientSpringConfiguration
import io.camunda.zeebe.spring.client.config.ZeebeClientStarterAutoConfiguration
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.context.annotation.Import
import java.util.*

@AutoConfiguration
@AutoConfigureAfter(
  ZeebeClientStarterAutoConfiguration::class
)
@Import(
  value = [
    ZeebeClientSpringConfiguration::class,
    ServiceTaskWorkerCamunda8Registry::class,
    Camunda8ZeebeWorkerConfiguration::class
  ]
)
class Camunda8Configuration