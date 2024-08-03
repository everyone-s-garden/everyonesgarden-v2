package com.garden.back.utils

import assertk.assertThat
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import com.garden.back.notification.utils.EmailUtils
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.parallel.Execution
import org.junit.jupiter.api.parallel.ExecutionMode

@Execution(ExecutionMode.CONCURRENT)
class EmailUtilsTest {

    @Test
    fun `type이 String이 아닌 경우 false를 return한다`() {
        listOf(
            123,
            true,
            false,
        ).forEach {
            assertThat(EmailUtils.isValid(it)).isFalse()
        }
    }

    @Test
    fun `null인 경우 false를 return한다`() {
        assertThat(EmailUtils.isValid(null)).isFalse()
    }

    @Test
    fun `올바르지 않은 이메일은 false를 return한다`() {
        listOf(
            "",
            "user",
            "user@example",
            "user @example.com",
            "user!@example.com",
            "user@@example.com",
            "user@example.",
            "user@example.c",
        ).forEach {
            assertThat(EmailUtils.isValid(it)).isFalse()
        }
    }

    @Test
    fun `올바른 email은 true를 return한다`() {
        listOf(
            "john.doe@example.com",
            "jane.smith@workplace.org",
            "contact@company.net",
            "first_last@domain.co.uk",
            "number123@domain123.io",
            "email.with+symbol@example.com",
            "underscore_in_name@domain.us",
            "firstname.lastname@university.edu",
            "simplealias@domain.info",
            "longaddressname@subdomain.company.com",
        ).forEach {
            assertThat(EmailUtils.isValid(it)).isTrue()
        }
    }
}
