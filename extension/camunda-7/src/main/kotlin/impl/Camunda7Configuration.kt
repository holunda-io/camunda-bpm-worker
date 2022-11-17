package io.holunda.camunda.worker.impl

import impl.RuntimeServiceProcessStarter
import io.holunda.camunda.worker.ProcessStarter
import org.camunda.bpm.engine.RuntimeService
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import

@AutoConfiguration
@Import(
  value = [
    ServiceTaskWorkerCamunda7Registry::class,
    Camunda7ExternalTaskClientConfiguration::class,
    Camunda7ExternalTaskServiceConfiguration::class,
  ]
)
@AutoConfigureAfter(
  // Camunda spring auto config ?
)
@EnableConfigurationProperties(value = [CamundaWorker7Properties::class])
class Camunda7Configuration {

  @Bean
  @ConditionalOnMissingBean
  fun camunda7processStarter(runtimeService: RuntimeService): ProcessStarter = RuntimeServiceProcessStarter(runtimeService = runtimeService)
}

