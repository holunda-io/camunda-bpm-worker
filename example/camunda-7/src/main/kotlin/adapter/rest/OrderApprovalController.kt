package io.holunda.camunda.worker.example.adapter.rest

import io.holunda.camunda.worker.example.usecase.OrderApproval
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/order/{orderId}")
class OrderApprovalController(
  val orderApproval: OrderApproval
) {

  @PostMapping("/start-approval")
  fun startApproval(@PathVariable("orderId") orderId: String): ResponseEntity<Void> {
    orderApproval.startOrderApproval(orderId)
    return accepted().build()
  }

  @GetMapping
  fun getOrderForApproval(@PathVariable("orderId") orderId: String): ResponseEntity<OrderDto> {
    return orderApproval.getOrderForApproval(orderId)?.let { order -> ok(order.toDto()) } ?: notFound().build()
  }

  @PostMapping("/approve")
  fun approveOrder(@PathVariable("orderId") orderId: String, @RequestBody approvalDecisionDto: ApprovalDecisionDto): ResponseEntity<Void> {
    orderApproval.approveOrder(orderId, approvalDecisionDto.toDomain())
    return noContent().build()
  }
}