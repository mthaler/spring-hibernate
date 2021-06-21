package com.mthaler.springhibernate.config

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.jdbc.core.JdbcTemplate

class CleanUp(private val jdbcTemplate: JdbcTemplate) {

    private fun destroy() {
        logger.info(" ... Deleting database files.")
        jdbcTemplate.execute("DROP ALL OBJECTS DELETE FILES;")
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(CleanUp::class.java)
    }
}