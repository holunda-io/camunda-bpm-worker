package io.holunda.camunda.worker.example.domain.calculation

import io.holunda.camunda.worker.example.domain.approval.OrderPosition
import org.jmolecules.architecture.onion.classical.DomainModelRing
import org.jmolecules.architecture.onion.classical.DomainServiceRing
import org.springframework.stereotype.Component
import java.math.BigDecimal

/**
 * Domain service to execute order calulations.
 */
@Component
@DomainServiceRing
class CalculationService {

  /**
   * Calculates the new total by adding the position costs to it.
   * @param total current total.
   * @param orderPosition position to add.
   * @return new total.
   */
  fun calculateTotal(total: BigDecimal, orderPosition: OrderPosition): BigDecimal {
    return total.plus(orderPosition.netCost.times(BigDecimal.valueOf(orderPosition.amount)))
  }

}
