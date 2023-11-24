package io.holunda.camunda.worker.example.infrastructure

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.github.projectmapk.jackson.module.kogera.KotlinModule
import org.camunda.spin.impl.json.jackson.format.JacksonJsonDataFormat
import org.camunda.spin.spi.DataFormatConfigurator

class KotlinJacksonDataFormatConfigurator : DataFormatConfigurator<JacksonJsonDataFormat> {

  companion object {
    fun configure(objectMapper: ObjectMapper) {
      objectMapper
        .registerModule(
          KotlinModule.Builder().build()
        )
        .registerModule(
          JavaTimeModule()
        )
        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
    }
  }
  override fun configure(dataFormat: JacksonJsonDataFormat) {
    configure(dataFormat.objectMapper)
  }

  override fun getDataFormatClass(): Class<JacksonJsonDataFormat> = JacksonJsonDataFormat::class.java
}
