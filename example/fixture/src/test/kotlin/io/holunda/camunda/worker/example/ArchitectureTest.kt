package io.holunda.camunda.worker.example

import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import io.holunda.camunda.worker.example.OrderFixtureRoot
import org.jmolecules.archunit.JMoleculesArchitectureRules
import java.net.URL


@AnalyzeClasses(packagesOf = [OrderFixtureRoot::class])
class ArchitectureTest {

  private val plantUmlDiagram: URL = requireNotNull(this.javaClass.getResource("domain.puml"))

  @ArchTest
  fun `follows onion architecture`(classes: JavaClasses) {
    JMoleculesArchitectureRules.ensureOnionClassical().check(classes)
  }

  @ArchTest
  fun `follows hexagonal architecture`(classes: JavaClasses) {
    JMoleculesArchitectureRules.ensureOnionClassical().check(classes)
  }


  /*
  @ArchTest
  fun `classes adhere dependencies in diagram`(classes: JavaClasses) {
    classes()
      .should(adhereToPlantUmlDiagram(plantUmlDiagram, consideringOnlyDependenciesInDiagram()))
      .check(classes)
  }
   */

}
