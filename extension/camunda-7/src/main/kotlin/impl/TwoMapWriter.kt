package io.holunda.camunda.worker.impl

import io.holunda.camunda.bpm.data.CamundaBpmData
import io.holunda.camunda.bpm.data.factory.VariableFactory
import io.holunda.camunda.bpm.data.writer.VariableWriter
import org.camunda.bpm.engine.variable.VariableMap
import java.util.function.Function

// FIXME: Should go to camunda-bpm-data
class TwoMapWriter(global: VariableMap, local: VariableMap) : VariableWriter<TwoMapWriter> {

  private val globalWriter = CamundaBpmData.writer(global)
  private val localWriter = CamundaBpmData.writer(local)

  override fun <T : Any?> setLocal(variableFactory: VariableFactory<T>, value: T): TwoMapWriter {
    localWriter.set(variableFactory, value)
    return this
  }

  override fun <T : Any?> setLocal(variableFactory: VariableFactory<T>, value: T, isTransient: Boolean): TwoMapWriter {
    localWriter.set(variableFactory, value, isTransient)
    return this
  }

  override fun <T : Any?> updateLocal(variableFactory: VariableFactory<T>, valueProcessor: Function<T, T>): TwoMapWriter {
    localWriter.update(variableFactory, valueProcessor)
    return this
  }

  override fun <T : Any?> updateLocal(variableFactory: VariableFactory<T>, valueProcessor: Function<T, T>, isTransient: Boolean): TwoMapWriter {
    localWriter.update(variableFactory, valueProcessor, isTransient)
    return this
  }

  override fun <T : Any?> removeLocal(variableFactory: VariableFactory<T>): TwoMapWriter {
    localWriter.remove(variableFactory)
    return this
  }

  override fun variablesLocal(): VariableMap {
    return localWriter.variables()
  }

  override fun <T : Any?> set(variableFactory: VariableFactory<T>, value: T): TwoMapWriter {
    globalWriter.set(variableFactory, value)
    return this
  }

  override fun <T : Any?> set(variableFactory: VariableFactory<T>, value: T, isTransient: Boolean): TwoMapWriter {
    globalWriter.set(variableFactory, value, isTransient)
    return this
  }

  override fun <T : Any?> update(variableFactory: VariableFactory<T>, valueProcessor: Function<T, T>): TwoMapWriter {
    globalWriter.update(variableFactory, valueProcessor)
    return this
  }

  override fun <T : Any?> update(variableFactory: VariableFactory<T>, valueProcessor: Function<T, T>, isTransient: Boolean): TwoMapWriter {
    globalWriter.update(variableFactory, valueProcessor, isTransient)
    return this
  }

  override fun <T : Any?> remove(variableFactory: VariableFactory<T>): TwoMapWriter {
    globalWriter.remove(variableFactory)
    return this
  }

  override fun variables(): VariableMap {
    return globalWriter.variables()
  }
}