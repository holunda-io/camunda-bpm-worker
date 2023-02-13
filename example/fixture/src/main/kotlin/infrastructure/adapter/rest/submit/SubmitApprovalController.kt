package io.holunda.camunda.worker.example.infrastructure.adapter.rest.submit

import io.holunda.camunda.worker.example.core.model.approval.OrderId
import io.holunda.camunda.worker.example.core.port.`in`.SubmitOrderInPort
import io.holunda.camunda.worker.example.infrastructure.adapter.rest.toDomain
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.*
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.util.*

/**
 * REST controller to submit orders.
 */
@RestController
class SubmitApprovalController(
  val submitOrderPort: SubmitOrderInPort
) {

  /**
   * Starts approval process for given order id.
   * @param orderId id of the order to approve.
   * @return acknowledge.
   */
  @PostMapping("/order/submit/{orderId}/start-approval")
  fun startApproval(@PathVariable("orderId") orderId: String): ResponseEntity<Void> {
    submitOrderPort.startApprovalOfExistingOrder(OrderId(orderId))
    return accepted().build()
  }

  /**
   * Submits an order for approval.
   * @return acknowledge, location header points to approve URL.
   */
  @PostMapping("/order/submit")
  fun submitOrder(@RequestBody submissionDto: OrderSubmissionDto): ResponseEntity<Void> {
    val orderId = submitOrderPort.submitOrder(submissionDto.toDomain())
    return created(
      ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/order/approve/{orderId}")
        .buildAndExpand(orderId.value)
        .toUri()
    ).build()
  }
}
