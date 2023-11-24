package io.holunda.camunda.worker.example.adapter.out.workflow

import io.holunda.camunda.bpm.data.CamundaBpmDataKotlin.booleanVariable
import io.holunda.camunda.bpm.data.CamundaBpmDataKotlin.customVariable
import io.holunda.camunda.bpm.data.CamundaBpmDataKotlin.stringVariable
import io.holunda.camunda.worker.example.domain.approval.Order
import io.holunda.camunda.worker.example.domain.approval.OrderPosition
import java.math.BigDecimal

object OrderApprovalProcess {
  const val KEY = "order-approval"

  val ORDER_ID = stringVariable("orderId").nonNull
  val ORDER = customVariable<Order?>("order")
  val ORDER_POSITION = customVariable<OrderPosition>("orderPosition")
  val ORDER_TOTAL = customVariable<BigDecimal>("orderTotal")
  val ORDER_APPROVED = booleanVariable("orderApproved").nonNull

  object Elements {
    const val TASK_APPROVE_ORDER = "user_approve_order"
  }
}
