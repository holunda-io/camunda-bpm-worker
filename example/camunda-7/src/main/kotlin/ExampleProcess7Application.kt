package io.holunda.camunda.worker.example

import org.camunda.bpm.engine.ProcessEngine
import org.camunda.bpm.engine.ProcessEngineServices
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication
import org.camunda.bpm.spring.boot.starter.configuration.CamundaProcessEngineConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean


fun main(args: Array<String>) = runApplication<ExampleProcess7Application>(*args).let { Unit }


@SpringBootApplication
@EnableProcessApplication
class ExampleProcess7Application {

  @Bean
  fun processEngineServices(processEngine: ProcessEngine): ProcessEngineServices = processEngine
}
