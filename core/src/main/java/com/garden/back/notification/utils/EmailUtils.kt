package com.garden.back.notification.utils

object EmailUtils {
    fun isEmailAddress(potentiallyEmail: Any?): Boolean {
        val email = potentiallyEmail as? String ?: return false
        return email.matches(Regex(EMAIL_REGEX))
    }

    private const val EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
}
