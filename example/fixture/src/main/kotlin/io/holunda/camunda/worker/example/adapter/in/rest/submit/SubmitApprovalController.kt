package io.holunda.camunda.worker.example.adapter.`in`.rest.submit

import io.holunda.camunda.worker.example.adapter.`in`.rest.RestMappers.toDomain
import io.holunda.camunda.worker.example.application.port.`in`.SubmitOrderInPort
import io.holunda.camunda.worker.example.domain.approval.OrderId
import org.jmolecules.architecture.hexagonal.PrimaryAdapter
import org.jmolecules.architecture.onion.classical.InfrastructureRing
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.accepted
import org.springframework.http.ResponseEntity.created
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

/**
 * REST controller to submit orders.
 */
@RestController
@PrimaryAdapter
@InfrastructureRing
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
