package unit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

public class UserValidationTest {

    @DisplayName("Валидный email должен проходить все проверки")
    @Test
    void shouldReturnTrueWhenEmailIsValid() {
        String email = "user@test.com";

        boolean isValid = isValidEmail(email);

        assertThat(isValid)
                .as("Email '%s' должен быть валидным", email)
                .isTrue();
    }

    @DisplayName("Email без @ должен быть невалидным")
    @Test
    void shouldReturnFalseWhenEmailDoesNotContainAt() {
        String email = "user.test.com";

        boolean isValid = isValidEmail(email);

        assertThat(isValid)
                .as("Email '%s' должен быть невалидным (нет @)", email)
                .isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "user@.com",
            "@example.com",
            "user@domain",
            "user@@example.com",
            "user@ex ample.com",
            "user@example.com.",
            ".user@example.com",
            "user@example..com",
            "user@-example.com",
            "user@example.c",
            "user@.example.com",
            "user@example-.com",
            "user@-example-.com"
    })
    @DisplayName("Множество невалидных email должны быть отклонены")
    void shouldRejectVariousInvalidEmails(String invalidEmail) {
        boolean isValid = isValidEmail(invalidEmail);

        assertThat(isValid)
                .as("Email '%s' должен быть невалидным", invalidEmail)
                .isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "user@test.com",
            "user.name@test.com",
            "user+filter@test.co.uk",
            "user_name@test.io",
            "123@test.com",
            "user@test-domain.com",
            "user@test.travel",
            "user@sub.domain.com",
            "user@domain.co.jp"
    })
    @DisplayName("Множество валидных email должны проходить проверку")
    void shouldAcceptVariousValidEmails(String validEmail) {
        boolean isValid = isValidEmail(validEmail);

        assertThat(isValid)
                .as("Email '%s' должен быть валидным", validEmail)
                .isTrue();
    }

    private boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }

        // Проверка на недопустимые символы и форматы
        if (!email.contains("@") ||
                email.startsWith("@") ||
                email.startsWith(".") ||
                email.endsWith(".") ||
                email.contains(" ") ||
                email.contains("..")) {
            return false;
        }

        // Проверка на количество @ (должна быть ровно одна)
        if (email.indexOf("@") != email.lastIndexOf("@")) {
            return false;
        }

        int atIndex = email.indexOf("@");
        int lastDotIndex = email.lastIndexOf(".");

        // @ должна быть перед последней точкой
        if (atIndex >= lastDotIndex) {
            return false;
        }

        // После @ должны быть символы до точки
        if (lastDotIndex <= atIndex + 1) {
            return false;
        }

        // Домен должен содержать точку
        String domain = email.substring(atIndex + 1);
        if (!domain.contains(".")) {
            return false;
        }

        // Проверка на дефис в начале/конце частей домена
        String[] domainParts = domain.split("\\.");
        for (String part : domainParts) {
            if (part.isEmpty() || part.startsWith("-") || part.endsWith("-")) {
                return false;
            }
        }

        // Последняя часть домена (TLD) должна быть минимум 2 символа
        String tld = domainParts[domainParts.length - 1];
        return tld.length() >= 2;
    }
}