package com.graden.back.global.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class EnumValueValidator implements ConstraintValidator<EnumValue, String> {

    private EnumValue enumClass;

    @Override
    public void initialize(EnumValue annotation) {
        enumClass = annotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Enum<?>[] enumValues = this.enumClass.enumClass().getEnumConstants();

        if (enumValues == null) return false;

        if (value == null) {
            return false;
        }

        return Arrays.stream(enumValues).anyMatch(enumValue -> value.equals(enumValue.toString()));
    }

}
