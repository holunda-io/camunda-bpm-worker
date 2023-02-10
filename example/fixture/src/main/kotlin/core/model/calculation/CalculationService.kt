package io.holunda.camunda.worker.example.core.model.calculation

import io.holunda.camunda.worker.example.core.model.approval.OrderPosition
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
