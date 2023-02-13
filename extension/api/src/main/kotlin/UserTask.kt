package io.holunda.camunda.worker

import io.holunda.camunda.bpm.data.reader.VariableReader

/**
 * Represents a user task.
 */
data class UserTask(
  val taskId: String,
  val type: String,
  val name: String,
  val description: String? = null,
  val reader: VariableReader
)
