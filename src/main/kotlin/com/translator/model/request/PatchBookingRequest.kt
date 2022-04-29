package com.translator.model.request

data class PatchBookingRequest(
    val bookingId: Int,
    val status: String?,
    val usedTimeInMinutes: Int?
)