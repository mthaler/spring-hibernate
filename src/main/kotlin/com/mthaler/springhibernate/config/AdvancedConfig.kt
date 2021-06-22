package com.mthaler.springhibernate.config

import org.apache.commons.dbcp2.BasicDataSource
import org.hibernate.SessionFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.io.IOException
import java.util.*
import javax.sql.DataSource
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;

@Configuration
@ComponentScan(basePackages = ["com.mthaler.springhibernate"])
@EnableTransactionManagement
@PropertySource("classpath:db/jdbc.properties")
class AdvancedConfig {

    @Value("\${driverClassName}")
    private val driverClassName: String? = null

    @Value("\${url}")
    private val url: String? = null

    @Value("\${username}")
    private val username: String? = null

    @Value("\${password}")
    private val password: String? = null

    @Bean(destroyMethod = "close")
    fun dataSource(): DataSource {
        return try {
            val dataSource = BasicDataSource()
            dataSource.driverClassName = driverClassName
            dataSource.url = url
            dataSource.username = username
            dataSource.password = password
            dataSource
        } catch (e: Exception) {
            logger.error("DBCP DataSource bean cannot be created!", e)
            throw e
        }
    }

    private fun hibernateProperties(): Properties {
        val hibernateProp = Properties()
        hibernateProp["hibernate.dialect"] = "org.hibernate.dialect.PostgreSQL9Dialect"
        hibernateProp["hibernate.hbm2ddl.auto"] = "create-drop"
        hibernateProp["hibernate.format_sql"] = true
        hibernateProp["hibernate.use_sql_comments"] = true
        hibernateProp["hibernate.show_sql"] = true
        hibernateProp["hibernate.max_fetch_depth"] = 3
        hibernateProp["hibernate.jdbc.batch_size"] = 10
        hibernateProp["hibernate.jdbc.fetch_size"] = 50
        return hibernateProp
    }

    @Bean
    fun sessionFactory(): SessionFactory {
        return LocalSessionFactoryBuilder(dataSource())
            .scanPackages("com.mthaler.springhibernate.entities")
            .addProperties(hibernateProperties())
            .buildSessionFactory()
    }

    @Bean
    @Throws(IOException::class)
    fun transactionManager(): PlatformTransactionManager {
        return HibernateTransactionManager(sessionFactory())
    }

    @Bean(destroyMethod = "destroy")
    fun cleanUp(): CleanUp {
        return CleanUp(JdbcTemplate(dataSource()))
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(AdvancedConfig::class.java)

        @JvmStatic
        @Bean
        fun propertySourcesPlaceholderConfigurer(): PropertySourcesPlaceholderConfigurer? {
            return PropertySourcesPlaceholderConfigurer()
        }
    }
}