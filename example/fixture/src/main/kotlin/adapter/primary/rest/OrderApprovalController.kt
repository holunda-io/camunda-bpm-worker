package io.holunda.camunda.worker.example.adapter.primary.rest

import io.holunda.camunda.worker.example.application.OrderApprovalPrimaryPort
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.*
import org.springframework.web.bind.annotation.*

/**
 * Rest controller to offering operation to interact with offers.
 */
@RestController
@RequestMapping("/order/{orderId}")
class OrderApprovalController(
  val orderApproval: OrderApprovalPrimaryPort
) {

  /**
   * Starts approval process for given order id.
   * @param orderId id of the order to approve.
   * @return acknowledge.
   */
  @PostMapping("/start-approval")
  fun startApproval(@PathVariable("orderId") orderId: String): ResponseEntity<Void> {
    orderApproval.startOrderApproval(orderId)
    return accepted().build()
  }

  /**
   * Retrieves order with a given id.
   * @param orderId id of the order.
   * @return order.
   */
  @GetMapping
  fun getOrderForApproval(@PathVariable("orderId") orderId: String): ResponseEntity<OrderDto> {
    return orderApproval.getOrderForApproval(orderId)?.let { order -> ok(order.toDto()) } ?: notFound().build()
  }

  /**
   * Approves order with given id.
   * @param orderId id of the order.
   * @param approvalDecisionDto approval decision.
   * @return acknowledge.
   */
  @PostMapping("/approve")
  fun approveOrder(@PathVariable("orderId") orderId: String, @RequestBody approvalDecisionDto: ApprovalDecisionDto): ResponseEntity<Void> {
    orderApproval.approveOrder(orderId, approvalDecisionDto.toDomain())
    return noContent().build()
  }
}
