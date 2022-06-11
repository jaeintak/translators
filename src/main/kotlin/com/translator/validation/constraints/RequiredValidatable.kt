//package com.translator.validation.constraints
//
//import com.translator.validation.RequiredValidator
//import javax.validation.Constraint
//import javax.validation.Payload
//import kotlin.reflect.KClass
//
//@Target(AnnotationTarget.CLASS)
//@Retention(AnnotationRetention.RUNTIME)
//@Constraint(validatedBy = [RequiredValidator::class])
//annotation class RequiredValidatable(
//    val message: String = "should be present",
//    val groups: Array<KClass<out Any>> = [],
//    val payload: Array<KClass<out Payload>> = []
//)
