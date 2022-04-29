package com.translator.persistence

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.util.Locale

class RoutingDataSource(
    private val connectionsByRequestHeader: Map<String, Map<String, List<String>>> = mapOf(),
    private val connectionsByRequestMethod: Map<String, List<String>> = mapOf()
) : AbstractRoutingDataSource() {

    override fun determineCurrentLookupKey(): Any? {
        val requestAttributes = RequestContextHolder.getRequestAttributes() as ServletRequestAttributes
        for ((header, connectionsByHeaderValue) in connectionsByRequestHeader) {
            val headerValue = requestAttributes.request.getHeader(header)
            if (connectionsByHeaderValue.containsKey(headerValue)) {
                return connectionsByHeaderValue[headerValue]?.random()
            }
        }
        val methods = connectionsByRequestMethod.keys.map { it.lowercase(Locale.getDefault()) }
        return when (val method = requestAttributes.request.method.lowercase(Locale.getDefault())) {
            in methods -> connectionsByRequestMethod[method]?.random()
            else -> null
        }
    }

}