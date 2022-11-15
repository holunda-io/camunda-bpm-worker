package io.holunda.camunda.worker.impl

import io.holunda.camunda.bpm.data.CamundaBpmData
import io.holunda.camunda.bpm.data.factory.VariableFactory
import io.holunda.camunda.bpm.data.writer.VariableWriter
import org.camunda.bpm.engine.variable.VariableMap
import java.util.function.Function

// FIXME -> move to camunda-bpm-data
class MapWriter(
  result: VariableMap,
  private val delegateLocalToGlobal: Boolean = true
) : VariableWriter<MapWriter> {

  private val writer = CamundaBpmData.writer(result)

  override fun <T : Any?> setLocal(variableFactory: VariableFactory<T>?, value: T): MapWriter {
    require(delegateLocalToGlobal) { "Access to local variables is not supported."}
    writer.set(variableFactory, value)
    return this
  }

  override fun <T : Any?> setLocal(variableFactory: VariableFactory<T>?, value: T, isTransient: Boolean): MapWriter {
    require(delegateLocalToGlobal) { "Access to local variables is not supported."}
    writer.set(variableFactory, value, isTransient)
    return this
  }

  override fun <T : Any?> updateLocal(variableFactory: VariableFactory<T>?, valueProcessor: Function<T, T>?): MapWriter {
    require(delegateLocalToGlobal) { "Access to local variables is not supported."}
    writer.update(variableFactory, valueProcessor)
    return this
  }

  override fun <T : Any?> updateLocal(variableFactory: VariableFactory<T>?, valueProcessor: Function<T, T>?, isTransient: Boolean): MapWriter {
    require(delegateLocalToGlobal) { "Access to local variables is not supported."}
    writer.update(variableFactory, valueProcessor, isTransient)
    return this
  }

  override fun <T : Any?> removeLocal(variableFactory: VariableFactory<T>?): MapWriter {
    require(delegateLocalToGlobal) { "Access to local variables is not supported."}
    writer.remove(variableFactory)
    return this
  }

  override fun variablesLocal(): VariableMap {
    require(delegateLocalToGlobal) { "Access to local variables is not supported."}
    return writer.variables()
  }

  override fun <T : Any?> set(variableFactory: VariableFactory<T>?, value: T): MapWriter {
    writer.set(variableFactory, value)
    return this
  }

  override fun <T : Any?> set(variableFactory: VariableFactory<T>?, value: T, isTransient: Boolean): MapWriter {
    writer.set(variableFactory, value, isTransient)
    return this
  }

  override fun <T : Any?> update(variableFactory: VariableFactory<T>?, valueProcessor: Function<T, T>?): MapWriter {
    writer.update(variableFactory, valueProcessor)
    return this
  }

  override fun <T : Any?> update(variableFactory: VariableFactory<T>?, valueProcessor: Function<T, T>?, isTransient: Boolean): MapWriter {
    writer.update(variableFactory, valueProcessor, isTransient)
    return this
  }

  override fun <T : Any?> remove(variableFactory: VariableFactory<T>?): MapWriter {
    writer.remove(variableFactory)
    return this
  }

  override fun variables(): VariableMap {
    return writer.variables()
  }
}