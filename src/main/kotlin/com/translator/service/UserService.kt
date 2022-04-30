package com.translator.service

import com.translator.model.request.PostUserRequest
import com.translator.repository.UserRepository
import org.jooq.translator.tables.records.UsersRecord
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val repository: UserRepository
) {

//    fun get(params: GetUserRequest): List<UsersRecord>{
//        return repository.find(params)
//    } is there a case where i would want to get all the users?

    fun getById(userId: Int): UsersRecord? {
        return repository.findById(userId)
    }

    @Transactional
    fun post(params: PostUserRequest): Int{
        return repository.create(params)
    }
//
//    @Transactional
//    fun patch(params: PatchUserRequest): Int{
//        return repository.update(params)
//    }
}