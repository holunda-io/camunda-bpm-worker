package io.holunda.camunda.worker

/**
 * An interface approach to make the topic available to the runtime.
 */
interface ServiceTypeAware {
  /**
   * Retrieves the service type.
   * @return type of the service.
   */
  fun getServiceType(): String
}
