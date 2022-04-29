package com.translator.configuration

import com.translator.configuration.properties.DatabaseProperties
import com.translator.persistence.RoutingDataSource
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy
import javax.sql.DataSource

@Configuration
@EnableConfigurationProperties(DatabaseProperties::class)
class DatabaseConfiguration(
    private val databaseProperties: DatabaseProperties
) {

    @Bean
    @ConditionalOnMissingBean
    fun dataSource(): DataSource =
        RoutingDataSource(
            databaseProperties.connectionsByRequestHeader,
            databaseProperties.connectionsByRequestMethod
        ).apply {
            setTargetDataSources(databaseProperties.connections)
            setDefaultTargetDataSource(
                databaseProperties.connections[databaseProperties.defaultConnection] as Any
            )
        }

    @Bean
    @ConditionalOnMissingBean
    fun dslContext(dataSource: DataSource): DSLContext =
        DSL.using(TransactionAwareDataSourceProxy(dataSource), SQLDialect.MYSQL)

}