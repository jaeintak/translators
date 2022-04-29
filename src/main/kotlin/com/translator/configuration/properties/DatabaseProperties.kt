package com.translator.configuration.properties

import com.zaxxer.hikari.HikariDataSource
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "database")
class DatabaseProperties {
    lateinit var defaultConnection: String
    lateinit var connections: Map<Any, HikariDataSource>
    var connectionsByRequestHeader = mapOf<String, Map<String, List<String>>>()
    var connectionsByRequestMethod = mapOf<String, List<String>>()
}