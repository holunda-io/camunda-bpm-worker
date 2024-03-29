package io.holunda.camunda.worker

/**
 * Access to user tasks.
 */
interface UserTaskWorker {

  /**
   * Finds a user task for given user task query.
   * @return UserTask or null
   */
  fun loadUserTask(query: UserTaskQuery) : UserTask?

  /**
   * Completes a user task.
   * @param taskId id of the task to complete.
   * @param userId optional user id.
   * @param variables variables to write to process instance during completion.
   */
  fun completeUserTask(taskId: String, userId: String? = null, variables: Map<String, Any> = mapOf())

}
