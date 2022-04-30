package com.translator.model.response

class UserResponse(
    val user : User
) {
    class User(
        val userId: Int,
        val name: String,
        val email: String
//        val cardInfo: String,
//        val bookingHistories: List<Booking>
    ){
        class Booking(

        )
    }
}