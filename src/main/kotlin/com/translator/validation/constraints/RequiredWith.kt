package com.translator.validation.constraints

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class RequiredWith(val fields: Array<String> = [])
