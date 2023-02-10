package io.holunda.camunda.worker.io.holunda.camunda.worker

/**
 * Task query.
 */
data class UserTaskQuery(
  val taskId: String? = null,
  val name: String? = null,
  val type: String? = null,
  val businessKey: String? = null,
  val variables: Map<String, Any> = mapOf()
)
