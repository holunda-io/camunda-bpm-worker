package impl

import io.camunda.operate.CamundaOperateClient
import io.camunda.operate.dto.FlownodeInstance
import io.camunda.operate.dto.Variable
import io.camunda.operate.search.SearchQuery
import io.camunda.operate.search.Sort
import io.camunda.operate.search.SortOrder
import io.camunda.operate.search.VariableFilter
import io.camunda.tasklist.CamundaTaskListClient
import io.camunda.zeebe.model.bpmn.instance.FlowNode

class TaskAccessor(
  private val taskListClient: CamundaTaskListClient,
  private val operateBetaClient: CamundaOperateClient
) {

  fun getTask(taskId: String) {
    taskListClient.getTask(taskId, true).variables

  }

  fun searchTaskByVariables() {

    val variableFilter = VariableFilter.Builder().processInstanceKey(4L).build()
    val varQuery = SearchQuery.Builder().withFilter(variableFilter).build()

    val variables: List<Variable> = operateBetaClient.searchVariables(
      varQuery
    )

    val flowNodes: List<FlownodeInstance> = operateBetaClient.searchFlownodeInstances(varQuery)

  }
}
