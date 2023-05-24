package io.holunda.camunda.worker.example.infrastructure

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.camunda.spin.impl.json.jackson.format.JacksonJsonDataFormat
import org.camunda.spin.spi.DataFormatConfigurator

class KotlinJacksonDataFormatConfigurator : DataFormatConfigurator<JacksonJsonDataFormat> {

  override fun configure(dataFormat: JacksonJsonDataFormat) {
    val objectMapper = dataFormat.objectMapper
    objectMapper.registerKotlinModule()
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
  }

  override fun getDataFormatClass(): Class<JacksonJsonDataFormat> = JacksonJsonDataFormat::class.java
}
