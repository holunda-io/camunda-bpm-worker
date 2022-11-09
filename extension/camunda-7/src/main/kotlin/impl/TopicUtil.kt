package impl

import io.holunda.camunda.bpm.data.reader.VariableReader
import io.holunda.camunda.bpm.data.writer.VariableWriter
import io.holunda.camunda.worker.ServiceTaskWorker
import io.holunda.camunda.worker.Topic
import io.holunda.camunda.worker.TopicAware
import org.springframework.core.annotation.AnnotationUtils

object TopicUtil {

  fun hasTopic(serviceTaskWorker: ServiceTaskWorker) = retrieveTopic(serviceTaskWorker) != null

  /**
   * Retrieves topic from service task worker.
   */
  fun retrieveTopic(serviceTaskWorker: ServiceTaskWorker): String? {
    if (serviceTaskWorker is TopicAware) {
      return serviceTaskWorker.getTopic()
    }
    val classAnnotation = AnnotationUtils.findAnnotation(serviceTaskWorker::class.java, Topic::class.java)
    val methodAnnotation = AnnotationUtils.findAnnotation(serviceTaskWorker::class.java.getMethod(ServiceTaskWorker::execute.name, VariableReader::class.java, VariableWriter::class.java), Topic::class.java)
    return when {
      methodAnnotation != null -> methodAnnotation.value
      classAnnotation != null -> classAnnotation.value
      else -> null
    }
  }
}
