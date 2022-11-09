package impl

import io.holunda.camunda.worker.impl.ExternalTaskServiceTaskWorkerRegistrar
import io.holunda.camunda.worker.impl.JavaDelegateServiceTaskWorkerRegistrar
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration // FIXME -> Autoconfig
@Import(
  value = [ExternalTaskServiceTaskWorkerRegistrar::class, JavaDelegateServiceTaskWorkerRegistrar::class]
)
class Camunda7Configuration {
}
