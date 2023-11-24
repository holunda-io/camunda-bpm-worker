package io.holunda.camunda.worker.example.infrastructure

import io.github.projectmapk.jackson.module.kogera.readValue
import io.holunda.camunda.worker.example.domain.approval.Order
import io.holunda.camunda.worker.example.domain.approval.OrderId
import io.holunda.camunda.worker.example.domain.approval.OrderPosition
import org.assertj.core.api.Assertions.assertThat
import org.camunda.spin.impl.json.jackson.format.JacksonJsonDataFormat
import org.camunda.spin.impl.json.jackson.format.JacksonJsonDataFormatProvider
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.Instant
import java.util.*
import org.camunda.bpm.client.variable.impl.format.json.JacksonJsonDataFormat as ClientJacksonJsonDataFormat
import org.camunda.bpm.client.variable.impl.format.json.JacksonJsonDataFormatProvider as ClientJacksonJsonDataFormatProvider


internal class DomainTypeSerializationTest {

  private val serverDataFormat = JacksonJsonDataFormatProvider().createInstance() as JacksonJsonDataFormat
  private val clientDataFormat = ClientJacksonJsonDataFormatProvider().createInstance() as ClientJacksonJsonDataFormat

  private val order = Order(
    orderId = OrderId("cdb43a24-4378-475a-8fa9-5bef51160e33"),
    created = Date.from(Instant.parse("2023-11-23T10:00:00Z")),
    positions = listOf(
      OrderPosition(
        title = "pen",
        netCost = BigDecimal.valueOf(10.00),
        amount = 2L
      ),
      OrderPosition(
        title = "pencil",
        netCost = BigDecimal.valueOf(12.00),
        amount = 2L
      )
    )
  )
  private val expectedJson = "{\"orderId\":\"cdb43a24-4378-475a-8fa9-5bef51160e33\",\"created\":\"2023-11-23T10:00:00.000+00:00\",\"positions\":[{\"title\":\"pen\",\"netCost\":10.0,\"amount\":2},{\"title\":\"pencil\",\"netCost\":12.0,\"amount\":2}]}"


  @BeforeEach
  fun `init object mapper`() {
    KotlinJacksonDataFormatConfigurator().configure(serverDataFormat)
    KotlinJacksonDataFormatClientConfigurator().configure(clientDataFormat)
  }


  @Test
  fun `should serialize to JSON and deserialize back using server side config`() {
    val objectMapper = serverDataFormat.objectMapper
    val json = objectMapper.writeValueAsString(order)
    assertThat(json).isEqualTo(expectedJson)
    val back = objectMapper.readValue<Order>(json)
    assertThat(back).isEqualTo(order)
  }

  @Test
  fun `should serialize to JSON and deserialize back using client side config`() {

    val objectMapper = clientDataFormat.objectMapper
    val json = objectMapper.writeValueAsString(order)
    assertThat(json).isEqualTo(expectedJson)
    val back = objectMapper.readValue<Order>(json)
    assertThat(back).isEqualTo(order)
  }

}
