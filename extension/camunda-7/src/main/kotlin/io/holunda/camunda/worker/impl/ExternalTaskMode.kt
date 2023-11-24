package io.holunda.camunda.worker.impl

/**
 * Different operation modes for the Camunda 7 implementation of the worker.
 */
enum class ExternalTaskMode {
  /**
   * Wrap workers as Java Delegates.
   */
  DELEGATE,

  /**
   * Encapsulate workers in handlers using Camunda's External Task Java REST client
   */
  CLIENT,
  /**
   * Encapsulate workers in handlers using Camunda's External Service Java API
   */
  SERVICE;
}