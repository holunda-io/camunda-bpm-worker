package io.holunda.camunda.worker.example.domain.service

import io.holunda.camunda.worker.example.domain.model.OrderPosition
import org.jmolecules.architecture.onion.classical.DomainServiceRing
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
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
