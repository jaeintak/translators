package com.translator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TranslatorApplication

fun main(args: Array<String>) {
    runApplication<TranslatorApplication>(*args)
}
