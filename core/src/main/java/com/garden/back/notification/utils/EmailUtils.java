package com.garden.back.notification.utils;

public final class EmailUtils {

    private EmailUtils() {
        throw new AssertionError("Utils class does not need to be initialized");
    }

    public static boolean isEmailAddress(Object potentiallyEmail) {
        if (potentiallyEmail instanceof String email) {
            return email.matches(REGEX);
        }
        return false;
    }

    public static String REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
}
