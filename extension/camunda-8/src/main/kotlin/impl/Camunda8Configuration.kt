package io.holunda.camunda.worker.impl

import io.camunda.operate.CamundaOperateClient
import io.camunda.tasklist.CamundaTaskListClient
import io.camunda.zeebe.client.ZeebeClient
import io.camunda.zeebe.spring.client.ZeebeClientSpringConfiguration
import io.camunda.zeebe.spring.client.config.ZeebeClientStarterAutoConfiguration
import io.camunda.zeebe.spring.client.properties.ZeebeClientConfigurationProperties
import io.holunda.camunda.worker.ProcessStarter
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import java.util.*
import io.camunda.operate.auth.SaasAuthentication as OperateSaasAuthentication
import io.camunda.tasklist.auth.SaasAuthentication as TasklistSaasAuthentication

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
class Camunda8Configuration {

  @Value("\${tasklist.url}")
  private lateinit var taskListUrl: String

  @Value("\${operate.url}")
  private lateinit var operateUrl: String

  @Bean
  @ConditionalOnMissingBean
  fun camunda8processStarter(zeebeClient: ZeebeClient): ProcessStarter = Camunda8ZeebeProcessStarter(zeebeClient)

  @Bean
  fun tasklistSaasAuthentication(zeebeClientCloudConfigurationProperties: ZeebeClientConfigurationProperties) = TasklistSaasAuthentication(
    zeebeClientCloudConfigurationProperties.cloud.clientId,
    zeebeClientCloudConfigurationProperties.cloud.clientSecret
  )

  @Bean
  fun operateSaasAuthentication(zeebeClientCloudConfigurationProperties: ZeebeClientConfigurationProperties) = OperateSaasAuthentication(
    zeebeClientCloudConfigurationProperties.cloud.clientId,
    zeebeClientCloudConfigurationProperties.cloud.clientSecret
  )


  @Bean
  fun camundaTaskListClient(auth: TasklistSaasAuthentication): CamundaTaskListClient = CamundaTaskListClient
    .Builder()
    .taskListUrl(taskListUrl)
    .authentication(auth)
    .build()


  @Bean
  fun operateClient(auth: OperateSaasAuthentication): CamundaOperateClient = CamundaOperateClient
    .Builder()
    .operateUrl(operateUrl)
    .authentication(auth)
    .build()

  @Bean
  fun taskListClient(
    camundaTaskListClient: CamundaTaskListClient,
    operateBetaClient: CamundaOperateClient
  ) = Camunda8TaskWorker(
    taskListClient = camundaTaskListClient,
    operateBetaClient = operateBetaClient
  )
}
