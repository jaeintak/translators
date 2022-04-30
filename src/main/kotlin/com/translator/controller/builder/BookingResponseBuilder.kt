package com.translator.controller.builder

import com.translator.model.response.BookingResponse
import com.translator.model.response.TranslatorResponse
import org.jooq.translator.tables.records.BookingsRecord
import org.jooq.translator.tables.records.TranslatorsRecord
import org.springframework.stereotype.Component

@Component
class BookingResponseBuilder() {

    fun build(booking:BookingsRecord?) = BookingResponse()

}