package com.translator.model.request

data class PostBookingRequest(
    val userId: Int,
    val translatorId: Int,

    //status can only be pending
    val status: String,
    val priceSnapshot: Int,
    val usedTimeInMinutes: Int,
    val languageFrom: String,
    val languageTo: String
)