package com.translator.api

import com.translator.controller.builder.BookingResponseBuilder
import com.translator.model.request.PatchBookingRequest
import com.translator.model.request.PostBookingRequest
import com.translator.model.response.BookingResponse
import com.translator.service.BookingService
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RestBookingController(
    private val service: BookingService,
    private val builder: BookingResponseBuilder
) {

    // when user makes booking, this api should be called
    @PostMapping("/api/booking")
    fun post(params: PostBookingRequest): BookingResponse {
        val bookingId = service.post(params)
        return builder.build(service.getById(bookingId))
    }

    // when translator accepts booking and when the call is over,
    // this api should be called
    @PatchMapping("/api/booking")
    fun patch(params: PatchBookingRequest): BookingResponse {
        val bookingId = service.patch(params)
        return builder.build(service.getById(bookingId))
    }
}