package io.holunda.camunda.worker.example

import com.fasterxml.jackson.databind.ObjectMapper
import io.holunda.camunda.worker.example.infrastructure.KotlinJacksonDataFormatConfigurator
import org.camunda.bpm.engine.ProcessEngine
import org.camunda.bpm.engine.ProcessEngineServices
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary

fun main(args: Array<String>) = runApplication<ExampleProcess7ExternalTaskApplication>(*args).let { Unit }

@SpringBootApplication
@EnableProcessApplication
class ExampleProcess7ExternalTaskApplication {

  @Bean
  fun processEngineServices(processEngine: ProcessEngine): ProcessEngineServices = processEngine

  @Bean
  @Primary
  fun objectMapper() = ObjectMapper().apply {
    KotlinJacksonDataFormatConfigurator.configure(this)
  }

}
