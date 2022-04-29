package com.translator.service

import com.translator.model.request.PatchBookingRequest
import com.translator.model.request.PostBookingRequest
import com.translator.repository.BookingRepository
import org.jooq.translator.tables.records.BookingsRecord
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookingService(
    private val repository: BookingRepository
) {

    fun getById(BookingId: Int): BookingsRecord? {
        return repository.findById(BookingId)
    }

    @Transactional
    fun post(params: PostBookingRequest): Int{
        //needs to send email or line/kakao alert
        return repository.create(params)
    }

    @Transactional
    fun patch(params: PatchBookingRequest): Int{
        //if status pending->establish : need to also generate zoom link
        return repository.update(params)
    }
}