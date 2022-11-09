package io.holunda.camunda.worker.example

import impl.Camunda7Configuration
import io.holunda.camunda.worker.impl.JavaDelegateServiceTaskWorkerRegistrar
import org.camunda.bpm.engine.ProcessEngine
import org.camunda.bpm.engine.ProcessEngineServices
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import

fun main(args: Array<String>) = runApplication<ExampleProcess7Application>(*args).let { Unit }

@SpringBootApplication
@EnableProcessApplication
@Import(value = [Camunda7Configuration::class])
class ExampleProcess7Application {

  @Bean
  fun processEngineServices(processEngine: ProcessEngine): ProcessEngineServices = processEngine
}
