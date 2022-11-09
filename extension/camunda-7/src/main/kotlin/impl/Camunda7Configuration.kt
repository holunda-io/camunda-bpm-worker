package impl

import io.holunda.camunda.worker.impl.ServiceTaskWorkerRegistrar
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.scheduling.annotation.EnableScheduling

@Configuration // FIXME -> Autoconfig
@Import(
  value = [ExternalTaskScheduler::class, ServiceTaskWorkerRegistrar::class]
)
@EnableScheduling
class Camunda7Configuration {
}
