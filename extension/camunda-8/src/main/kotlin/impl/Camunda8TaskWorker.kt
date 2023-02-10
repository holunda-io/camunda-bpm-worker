package io.holunda.camunda.worker.impl

import io.camunda.operate.CamundaOperateClient
import io.camunda.operate.dto.FlownodeInstance
import io.camunda.operate.dto.Variable
import io.camunda.operate.search.FlownodeInstanceFilter
import io.camunda.operate.search.SearchQuery
import io.camunda.operate.search.VariableFilter
import io.camunda.tasklist.CamundaTaskListClient
import io.holunda.camunda.worker.UserTaskWorker
import io.holunda.camunda.worker.io.holunda.camunda.worker.UserTask
import io.holunda.camunda.worker.io.holunda.camunda.worker.UserTaskQuery

class Camunda8TaskWorker(
  private val taskListClient: CamundaTaskListClient,
  private val operateBetaClient: CamundaOperateClient
) : UserTaskWorker {

  fun getTask(taskId: String) {
    taskListClient.getTask(taskId, true).variables

  }

  fun searchTaskByVariables() {

    val flowNode = FlownodeInstanceFilter.Builder().type("USER_TASK").build()
    val flowQuery = SearchQuery.Builder().withFilter(flowNode).build()
    val flowNodes: List<FlownodeInstance> = operateBetaClient.searchFlownodeInstances(flowQuery)


    val variableFilter = VariableFilter.Builder().processInstanceKey(4L).build()
    val varQuery = SearchQuery.Builder().withFilter(variableFilter).build()
    val variables: List<Variable> = operateBetaClient.searchVariables(varQuery)

  }

  override fun loadUserTask(query: UserTaskQuery): UserTask? {
    TODO("Not yet implemented")
  }

  override fun completeUserTask(taskId: String, userId: String?, variables: Map<String, Any>) {
    TODO("Not yet implemented")
  }
}
