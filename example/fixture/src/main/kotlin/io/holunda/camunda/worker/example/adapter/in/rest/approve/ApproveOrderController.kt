package io.holunda.camunda.worker.example.adapter.`in`.rest.approve

import io.holunda.camunda.worker.example.adapter.`in`.rest.RestMappers.toDomain
import io.holunda.camunda.worker.example.adapter.`in`.rest.RestMappers.toDto
import io.holunda.camunda.worker.example.application.port.`in`.ApproveOrderPort
import io.holunda.camunda.worker.example.domain.approval.OrderId
import jakarta.validation.Valid
import org.jmolecules.architecture.hexagonal.PrimaryAdapter
import org.jmolecules.architecture.onion.classical.InfrastructureRing
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.*
import org.springframework.web.bind.annotation.*

/**
 * Rest controller to access the "approve order" use case.
 */
@RestController
@RequestMapping("/order/approve/{orderId}")
@InfrastructureRing
@PrimaryAdapter
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
