package com.translator.repository

import com.translator.model.request.GetTranslatorRequest
import com.translator.model.request.PostTranslatorRequest
import org.jooq.DSLContext
import org.jooq.translator.Tables.USERS
import org.jooq.translator.enums.USERSStatus
import org.jooq.translator.tables.records.USERSRecord
import org.jooq.types.UInteger
import org.springframework.stereotype.Repository

@Repository
class UserRepository(
    private val dsl: DSLContext
) {
    fun find(params: GetTranslatorRequest): List<USERSRecord> {
        val query = dsl.selectQuery(USERS)
        query.addSelect(
            USERS.TRANSLATOR_ID,
            USERS.NAME,
            USERS.STATUS,
            USERS.PRICE,
            USERS.AVAILABLE_TIME_IN_MINUTES,
            USERS.LANGUAGE_FROM,
            USERS.LANGUAGE_TO
        )
        query.addConditions(USERS.STATUS.eq(USERSStatus.active))
        if (params.name != null) {
            query.addConditions(USERS.NAME.eq(params.name))
        }
        if (params.priceMax != null) {
            query.addConditions(USERS.PRICE.lessOrEqual(UInteger.valueOf(params.priceMax)))
        }
        if (params.languageFrom != null) {
            query.addConditions(USERS.LANGUAGE_FROM.eq(params.languageFrom))
        }
        if (params.languageTo != null) {
            query.addConditions(USERS.LANGUAGE_TO.eq(params.languageTo))
        }
        return query.fetchInto(USERS)
    }

    fun findById(translatorId: Int): USERSRecord? {
        val query = dsl.selectQuery(USERS)
        query.addSelect(
            USERS.TRANSLATOR_ID,
            USERS.NAME,
            USERS.STATUS,
            USERS.PRICE,
            USERS.AVAILABLE_TIME_IN_MINUTES,
            USERS.LANGUAGE_FROM,
            USERS.LANGUAGE_TO
        )
        query.addConditions(USERS.TRANSLATOR_ID.eq(UInteger.valueOf(translatorId)))
        return query.fetchOneInto(USERS)
    }

    fun create(params: PostTranslatorRequest): Int {
        dsl.insertInto(
            USERS,
            USERS.NAME,
            USERS.STATUS,
            USERS.PRICE,
            USERS.AVAILABLE_TIME_IN_MINUTES,
            USERS.LANGUAGE_FROM,
            USERS.LANGUAGE_TO
        ).values(
            params.name,
            params.status,
            UInteger.valueOf(params.price),
            UInteger.valueOf(params.availableTimeInMinutes),
            params.languageFrom,
            params.languageTo
        ).execute()
        val translatorId = dsl.lastID()
        return translatorId.toInt()
    }

    fun
}