package com.leaf.lolground.application.api.subscriber

import com.leaf.lolground.application.annotations.ApiController
import com.leaf.lolground.domain.subscriber.SubscriberService
import com.leaf.lolground.domain.subscriber.dto.SubscriberRegistrationDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@ApiController
class SubscriberController(
    val subscriberService: SubscriberService,
) {

    @PostMapping("/subscribers")
    fun postSubscribers(@RequestBody subscriberRegistrationDto: SubscriberRegistrationDto): ResponseEntity<Any?> {
        subscriberService.save(subscriberRegistrationDto)
        return ResponseEntity.ok(null)
    }

}
