package io.holunda.camunda.worker.impl

import io.holunda.camunda.bpm.data.CamundaBpmData.reader
import io.holunda.camunda.worker.UserTaskWorker
import io.holunda.camunda.worker.UserTask
import io.holunda.camunda.worker.UserTaskQuery
import org.camunda.bpm.engine.TaskService
import org.camunda.bpm.engine.task.Task

class TaskServiceUserTaskWorker(
  private val taskService: TaskService
) : UserTaskWorker {

  override fun loadUserTask(query: UserTaskQuery): UserTask? {
    val taskQuery = taskService.createTaskQuery()
      .active()
    if (query.taskId != null) {
      taskQuery.taskId(query.taskId)
    }
    if (query.businessKey != null) {
      taskQuery.processInstanceBusinessKey(query.businessKey)
    }
    if (query.type != null) {
      taskQuery.taskDefinitionKey(query.type)
    }
    if (query.name != null) {
      taskQuery.taskName(query.name)
    }
    query.variables.forEach { variable ->
      taskQuery.processVariableValueEquals(variable.key, variable.value)
    }
    val task: Task? = taskQuery.singleResult()
    return task?.let {
      UserTask(
        taskId = it.id,
        type = it.taskDefinitionKey,
        name = task.name,
        description = task.description,
        reader = reader(taskService, it.id)
      )
    }
  }

  override fun completeUserTask(taskId: String, userId: String?, variables: Map<String, Any>) {
    if (userId != null) { // FIXME: Maybe
      taskService.claim(taskId, userId)
    }
    taskService.complete(taskId, variables)
  }
}
