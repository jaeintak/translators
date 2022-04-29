package com.translator.repository

import com.translator.constant.BookingConstant
import com.translator.model.request.PatchBookingRequest
import com.translator.model.request.PostBookingRequest
import org.jooq.DSLContext
import org.jooq.translator.Tables.TRANSLATORS
import org.jooq.translator.enums.BookingsStatus
import org.jooq.translator.tables.Bookings.BOOKINGS
import org.jooq.translator.tables.records.BookingsRecord
import org.jooq.types.UInteger
import org.springframework.stereotype.Repository

@Repository
class BookingRepository(
    private val dsl: DSLContext
) {

    fun findById(translatorId: Int): BookingsRecord? {
        val query = dsl.selectQuery(BOOKINGS)
        query.addSelect(
            BOOKINGS.USER_ID,
            BOOKINGS.TRANSLATOR_ID,
            BOOKINGS.STATUS,
            BOOKINGS.PRICE_SNAPSHOT,
            BOOKINGS.USED_TIME_IN_MINUTES,
            BOOKINGS.LANGUAGE_FROM,
            BOOKINGS.LANGUAGE_TO
        )
        query.addConditions(TRANSLATORS.TRANSLATOR_ID.eq(UInteger.valueOf(translatorId)))
        return query.fetchOneInto(BOOKINGS)
    }

    fun create(params: PostBookingRequest): Int {
        dsl.insertInto(
            BOOKINGS,
            BOOKINGS.USER_ID,
            BOOKINGS.TRANSLATOR_ID,
            BOOKINGS.STATUS,
            BOOKINGS.PRICE_SNAPSHOT,
            BOOKINGS.USED_TIME_IN_MINUTES,
            BOOKINGS.LANGUAGE_FROM,
            BOOKINGS.LANGUAGE_TO
        ).values(
            UInteger.valueOf(params.userId),
            UInteger.valueOf(params.translatorId),
            convertStatus(params.status),
            UInteger.valueOf(params.priceSnapshot),
            UInteger.valueOf(params.usedTimeInMinutes),
            params.languageFrom,
            params.languageTo
        ).execute()
        val translatorId = dsl.lastID()
        return translatorId.toInt()
    }

    fun update(params: PatchBookingRequest): Int {
        val query = dsl.updateQuery(BOOKINGS)
        if(params.status != null){
            query.addValue(BOOKINGS.STATUS, convertStatus(params.status))
        }
        if(params.usedTimeInMinutes != null){
            query.addValue(BOOKINGS.USED_TIME_IN_MINUTES, UInteger.valueOf(params.usedTimeInMinutes))
        }
        return query.execute()
    }

    private fun convertStatus(status: String) =
        when (status) {
            BookingConstant.BOOKING_STATUS_PENDING -> BookingsStatus.pending
            BookingConstant.BOOKING_STATUS_ESTABLISHED -> BookingsStatus.established
            BookingConstant.BOOKING_STATUS_CANCELLED -> BookingsStatus.cancelled
            else -> null
        }
}