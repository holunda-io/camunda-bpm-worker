package io.holunda.camunda.worker.impl

import impl.ExternalTaskScheduler
import impl.TopicUtil
import io.holunda.camunda.bpm.data.CamundaBpmData.reader
import io.holunda.camunda.bpm.data.CamundaBpmData.writer
import io.holunda.camunda.bpm.data.factory.VariableFactory
import io.holunda.camunda.bpm.data.writer.GlobalVariableWriter
import io.holunda.camunda.bpm.data.writer.VariableMapWriter
import io.holunda.camunda.bpm.data.writer.VariableWriter
import io.holunda.camunda.worker.ServiceTaskWorker
import mu.KLogging
import org.camunda.bpm.engine.ExternalTaskService
import org.camunda.bpm.engine.delegate.BpmnError
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.camunda.bpm.engine.variable.VariableMap
import org.camunda.bpm.engine.variable.Variables
import org.springframework.beans.factory.config.BeanPostProcessor
import java.util.function.Function

/**
 * Java Delegate Service Task Worker.
 */
class ServiceTaskWorkerRegistrar(
  private val externalTaskService: ExternalTaskService,
  private val externalTaskScheduler: ExternalTaskScheduler
) : BeanPostProcessor {

  companion object : KLogging()

  private fun registerJavaDelegate(serviceTaskWorker: ServiceTaskWorker): JavaDelegate {
    return JavaDelegate { delegateExecution ->
      serviceTaskWorker.execute(reader(delegateExecution), writer(delegateExecution))
    }
  }

  private fun registerExternalTaskWorker(serviceTaskWorker: ServiceTaskWorker) {

    val worker = {
      val workerId = serviceTaskWorker.javaClass.canonicalName
      externalTaskService
        .fetchAndLock(10, workerId)
        .topic(TopicUtil.retrieveTopic(serviceTaskWorker), 5)
        .enableCustomObjectDeserialization()
        .execute()
        .forEach { lockedTask ->
          logger.info { "Processing task ${lockedTask.id}" }
          val resultingGlobalVariables = Variables.createVariables()
          val resultingLocalVariables = Variables.createVariables()
          val writer = TwoMapWriter(global = resultingGlobalVariables, local = resultingLocalVariables)
          try {
            serviceTaskWorker.execute(reader = reader(lockedTask), writer = writer)
            externalTaskService.complete(lockedTask.id, workerId, resultingGlobalVariables, resultingLocalVariables)
          } catch (e: Exception) {
            logger.error(e) { "Error in worker: $workerId" }
            when (e) {
              is BpmnError -> externalTaskService.handleBpmnError(lockedTask.id, workerId, e.errorCode, e.message)
              else -> externalTaskService.handleFailure(lockedTask.id, workerId, e.message, 1, 30)
            }
          }
        }
    }
    externalTaskScheduler.addWorker(worker)
  }

  override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any {
    if (bean is ServiceTaskWorker) {
      if (TopicUtil.hasTopic(bean)) {
        logger.info { "Found external task service: ${bean::class.java.canonicalName}, $beanName" }
        registerExternalTaskWorker(serviceTaskWorker = bean)
      }
      logger.info { "Registering it as a Java process: ${bean::class.java.canonicalName}, $beanName" }
      return registerJavaDelegate(serviceTaskWorker = bean)
    }
    return bean
  }
}

class TwoMapWriter(global: VariableMap, local: VariableMap): VariableWriter<TwoMapWriter> {

  private val globalWriter = writer(global)
  private val localWriter = writer(local)

  override fun <T : Any?> setLocal(variableFactory: VariableFactory<T>?, value: T): TwoMapWriter {
    localWriter.set(variableFactory, value)
    return this
  }

  override fun <T : Any?> setLocal(variableFactory: VariableFactory<T>?, value: T, isTransient: Boolean): TwoMapWriter {
    localWriter.set(variableFactory, value, isTransient)
    return this
  }

  override fun <T : Any?> updateLocal(variableFactory: VariableFactory<T>?, valueProcessor: Function<T, T>?): TwoMapWriter {
    localWriter.update(variableFactory, valueProcessor)
    return this
  }

  override fun <T : Any?> updateLocal(variableFactory: VariableFactory<T>?, valueProcessor: Function<T, T>?, isTransient: Boolean): TwoMapWriter {
    localWriter.update(variableFactory, valueProcessor, isTransient)
    return this
  }

  override fun <T : Any?> removeLocal(variableFactory: VariableFactory<T>?): TwoMapWriter {
    localWriter.remove(variableFactory)
    return this
  }

  override fun variablesLocal(): VariableMap {
    return localWriter.variables()
  }

  override fun <T : Any?> set(variableFactory: VariableFactory<T>?, value: T): TwoMapWriter {
    globalWriter.set(variableFactory, value)
    return this
  }

  override fun <T : Any?> set(variableFactory: VariableFactory<T>?, value: T, isTransient: Boolean): TwoMapWriter {
    globalWriter.set(variableFactory, value, isTransient)
    return this
  }

  override fun <T : Any?> update(variableFactory: VariableFactory<T>?, valueProcessor: Function<T, T>?): TwoMapWriter {
    globalWriter.update(variableFactory, valueProcessor)
    return this
  }

  override fun <T : Any?> update(variableFactory: VariableFactory<T>?, valueProcessor: Function<T, T>?, isTransient: Boolean): TwoMapWriter {
    globalWriter.update(variableFactory, valueProcessor, isTransient)
    return this
  }

  override fun <T : Any?> remove(variableFactory: VariableFactory<T>?): TwoMapWriter {
    globalWriter.remove(variableFactory)
    return this
  }

  override fun variables(): VariableMap {
    return globalWriter.variables()
  }
}
