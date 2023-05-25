package io.holunda.camunda.worker.example

import io.camunda.zeebe.spring.client.annotation.Deployment
import org.springframework.context.annotation.Configuration

/**
 * FIXME: Deployment Annotation doesn't work on Application Class (ExampleProcess8WorkerApplication) itself ...
 *
 * It gets not processed by post bean processor because of this:
 *
 * Bean 'exampleProcess8WorkerApplication' of type \[io.holunda.camunda.worker.example.ExampleProcess8WorkerApplication$$EnhancerBySpringCGLIB$$31bc5e6f] is not eligible for getting processed by all BeanPostProcessors (for example: not eligible for auto-proxying)
 *
 * (ZeebeDeploymentAnnotationProcessor gets not called for the application class)
 */
@Configuration
@Deployment(resources = ["classpath:order_approval_external.bpmn"])
class ExampleProcess8WorkerConfiguration {
}
