package io.holunda.camunda.worker

/**
 * Abstraction for a BPMN Error independent of implementation.
 */
class ServiceTaskBpmnError(
  val errorCode: String,
  val errorMessage: String
) : RuntimeException(errorMessage)
