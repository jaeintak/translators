package com.translator.validation.constraints

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class RequiredWithout(val fields: Array<String> = [])
