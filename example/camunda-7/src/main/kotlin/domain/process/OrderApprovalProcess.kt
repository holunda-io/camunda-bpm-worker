package io.holunda.camunda.worker.example.domain.process

import io.holunda.camunda.bpm.data.CamundaBpmDataKotlin.booleanVariable
import io.holunda.camunda.bpm.data.CamundaBpmDataKotlin.customVariable
import io.holunda.camunda.bpm.data.CamundaBpmDataKotlin.stringVariable
import io.holunda.camunda.worker.example.domain.model.Order
import io.holunda.camunda.worker.example.domain.model.OrderPosition
import java.math.BigDecimal

object OrderApprovalProcess {
  const val KEY = "order-approval"

  val ORDER_ID = stringVariable("orderId")
  val ORDER = customVariable<Order>("order")
  val ORDER_APPROVED = booleanVariable("orderApproved")
  val ORDER_POSITION = customVariable<OrderPosition>("orderPosition")
  val ORDER_TOTAL = customVariable<BigDecimal>("orderTotal")

  object Elements {
    const val TASK_APPROVE_ORDER = "user_approve_order"
  }
}
