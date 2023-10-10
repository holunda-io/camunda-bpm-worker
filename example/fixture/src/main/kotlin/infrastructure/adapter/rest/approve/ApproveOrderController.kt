package io.holunda.camunda.worker.example.infrastructure.adapter.rest.approve

import io.holunda.camunda.worker.example.core.model.approval.OrderId
import io.holunda.camunda.worker.example.core.port.`in`.ApproveOrderPort
import io.holunda.camunda.worker.example.infrastructure.adapter.rest.toDomain
import io.holunda.camunda.worker.example.infrastructure.adapter.rest.toDto
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.*
import org.springframework.web.bind.annotation.*
import jakarta.validation.Valid

/**
 * Rest controller to access the "approve order" use case.
 */
@RestController
@RequestMapping("/order/approve/{orderId}")
class ApproveOrderController(
  val approveOrderPort: ApproveOrderPort
) {

  /**
   * Retrieves order with a given id.
   * @param orderId id of the order.
   * @return order.
   */
  @GetMapping
  fun getOrderForApproval(@PathVariable("orderId") orderId: String): ResponseEntity<OrderDto> {
    return approveOrderPort.getOrderForApproval(OrderId(orderId))?.let { order -> ok(order.toDto()) } ?: notFound().build()
  }

  /**
   * Approves order with given id.
   * @param orderId id of the order.
   * @param approvalDecisionDto approval decision.
   * @return acknowledge.
   */
  @PostMapping
  fun approveOrder(@PathVariable("orderId") orderId: String, @Valid @RequestBody approvalDecisionDto: ApprovalDecisionDto): ResponseEntity<Void> {
    approveOrderPort.approveOrder(OrderId(orderId), approvalDecisionDto.toDomain())
    return noContent().build()
  }
}
