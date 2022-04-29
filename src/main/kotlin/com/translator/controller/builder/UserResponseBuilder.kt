package com.translator.controller.builder

import com.translator.model.response.TranslatorResponse
import org.jooq.translator.tables.records.TranslatorsRecord
import org.jooq.translator.tables.records.UsersRecord
import org.springframework.stereotype.Component

@Component
class UserResponseBuilder() {

    fun build(user:UsersRecord){}

}