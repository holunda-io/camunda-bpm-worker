package io.holunda.camunda.worker

interface TopicAware {
  fun getTopic(): String
}
