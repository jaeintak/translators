package com.translator.repository

import com.translator.model.request.GetUserRequest
import com.translator.model.request.PostUserRequest
import org.jooq.DSLContext
import org.jooq.translator.Tables.USERS
import org.jooq.translator.tables.records.UsersRecord
import org.jooq.types.UInteger
import org.springframework.stereotype.Repository

@Repository
class UserRepository(
    private val dsl: DSLContext
) {
    fun findById(userId: Int): UsersRecord? {
        val query = dsl.selectQuery(USERS)
        query.addSelect(
            USERS.USER_ID,
            USERS.NAME,
            USERS.EMAIL
        )
        query.addConditions(USERS.USER_ID.eq(UInteger.valueOf(userId)))
        return query.fetchOneInto(USERS)
    }

    fun create(request: PostUserRequest): Int {
        dsl.insertInto(
            USERS,
            USERS.NAME,
            USERS.EMAIL
        ).values(
            request.name,
            request.email
        ).execute()
        val translatorId = dsl.lastID()
        return translatorId.toInt()
    }
//
//    fun
}