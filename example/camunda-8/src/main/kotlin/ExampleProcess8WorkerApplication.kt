package io.holunda.camunda.worker.example

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.camunda.zeebe.client.api.JsonMapper
import io.camunda.zeebe.client.impl.ZeebeObjectMapper
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

fun main(args: Array<String>) = runApplication<ExampleProcess8WorkerApplication>(*args).let { Unit }

@SpringBootApplication
class ExampleProcess8WorkerApplication {
  @Bean
  fun jsonMapper(): JsonMapper {
    return ZeebeObjectMapper(
      ObjectMapper()
        .registerModule(KotlinModule.Builder().build())
        .registerModule(JavaTimeModule())
        .configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true)
    )
  }
}
