package io.holunda.camunda.worker.example.infrastructure

import io.github.projectmapk.jackson.module.kogera.readValue
import io.holunda.camunda.worker.example.ExampleProcess7ExternalTaskApplication
import io.holunda.camunda.worker.example.adapter.`in`.rest.OrderPositionDto
import io.holunda.camunda.worker.example.adapter.`in`.rest.submit.OrderSubmissionDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.Instant
import java.util.*


internal class AdapterTypeSerializationTest {

  private val objectMapper = ExampleProcess7ExternalTaskApplication().objectMapper()
  private val orderSubmission = OrderSubmissionDto(
    created = Date.from(Instant.parse("2023-11-23T10:00:00Z")),
    positions = listOf(
      OrderPositionDto(
        title = "pen",
        netCost = "10.00",
        amount = 2L
      ),
      OrderPositionDto(
        title = "pencil",
        netCost = "12.00",
        amount = 2L
      )
    )
  )
  private val expectedJson =
    "{\"created\":\"2023-11-23T10:00:00.000+00:00\",\"positions\":[{\"title\":\"pen\",\"netCost\":\"10.00\",\"amount\":2},{\"title\":\"pencil\",\"netCost\":\"12.00\",\"amount\":2}]}"


  @Test
  fun `should serialize to JSON and deserialize back`() {
    val json = objectMapper.writeValueAsString(orderSubmission)
    assertThat(json).isEqualTo(expectedJson)
    val back = objectMapper.readValue<OrderSubmissionDto>(json)
    assertThat(back).isEqualTo(orderSubmission)
  }
}
