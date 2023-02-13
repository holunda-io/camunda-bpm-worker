package io.holunda.camunda.worker.impl

import com.fasterxml.jackson.databind.ObjectMapper
import io.camunda.operate.CamundaOperateClient
import io.camunda.tasklist.CamundaTaskListClient
import io.camunda.tasklist.dto.Task
import io.camunda.tasklist.dto.TaskState
import io.holunda.camunda.worker.UserTask
import io.holunda.camunda.worker.UserTaskQuery
import io.holunda.camunda.worker.UserTaskWorker

class Camunda8TaskWorker(
  private val taskListClient: CamundaTaskListClient,
  private val objectMapper: ObjectMapper
) : UserTaskWorker {

  fun getTask(taskId: String) {
    taskListClient.getTask(taskId, true).variables
  }

  override fun loadUserTask(query: UserTaskQuery): UserTask? {
    return if (query.taskId != null) {
      taskListClient.getTask(query.taskId, true)?.toUserTask()
    } else {
      taskListClient.getTasks(false, TaskState.CREATED, true, 100)
        .firstOrNull { it.matches(query) }?.toUserTask()
    }
  }

  override fun completeUserTask(taskId: String, userId: String?, variables: Map<String, Any>) {
    if (userId != null) {
      taskListClient.claim(taskId, userId)
    }
    taskListClient.completeTask(taskId, variables)
  }

  private fun Task.toUserTask() = UserTask(
    this.id,
    this.formKey ?: "",
    this.name,
    null,
    reader = MapReader(objectMapper = objectMapper, json = this.variables.associate { it.name to it.value })
  )

  private fun Task.matches(query: UserTaskQuery): Boolean {
    return query.variables.all { queryVariable ->
      this.variables.map { it.name to it.value }.contains(queryVariable.key to queryVariable.value)
    }
  }
}
