package io.holunda.camunda.worker.impl

import io.holunda.camunda.bpm.data.reader.VariableReader
import io.holunda.camunda.bpm.data.writer.VariableWriter
import io.holunda.camunda.worker.ServiceTaskWorker
import io.holunda.camunda.worker.ServiceType
import io.holunda.camunda.worker.ServiceTypeAware
import org.springframework.core.annotation.AnnotationUtils

/**
 * Helper util.
 */
object TopicUtil {

  /**
   * Checks if the worker has a configured topic.
   */
  @JvmStatic
  fun hasServiceType(serviceTaskWorker: ServiceTaskWorker) = retrieveServiceType(serviceTaskWorker) != null

  /**
   * Retrieves topic from service task worker.
   */
  @JvmStatic
  fun retrieveServiceType(serviceTaskWorker: ServiceTaskWorker): String? {
    if (serviceTaskWorker is ServiceTypeAware) {
      return serviceTaskWorker.getServiceType()
    }
    val classAnnotation = AnnotationUtils.findAnnotation(serviceTaskWorker::class.java, ServiceType::class.java)
    val methodAnnotation = AnnotationUtils.findAnnotation(
      serviceTaskWorker::class.java.getMethod(ServiceTaskWorker::execute.name, VariableReader::class.java, VariableWriter::class.java),
      ServiceType::class.java
    )
    return when {
      methodAnnotation != null -> methodAnnotation.value
      classAnnotation != null -> classAnnotation.value
      else -> null
    }
  }
}
