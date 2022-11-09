package impl

import mu.KLogging
import org.springframework.scheduling.annotation.Scheduled

typealias Worker = () -> Unit

class ExternalTaskScheduler {

  private val workers: MutableList<Worker> = mutableListOf()

  companion object : KLogging()

  // FIXME -> replace by trigger of a parse listener
  @Scheduled(fixedDelayString = "PT10S", initialDelayString = "PT15S")
  fun scheduleTasks() {
    if (workers.isNotEmpty()) {
      // logger.info { "Running workers" }
      workers.forEach {
        it.invoke()
      }
    }
  }

  fun addWorker(worker: Worker) {
    workers.add(worker)
  }
}
