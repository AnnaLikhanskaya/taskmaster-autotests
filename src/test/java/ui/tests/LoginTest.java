package ui.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ui.pages.LoginPage;

import static com.codeborne.selenide.Condition.text;
import static ui.pages.BasePage.setUp;

@DisplayName("Тесты страницы логина")
public class LoginTest {

    private LoginPage loginPage;

    @BeforeEach
    void init() {
        setUp();
        loginPage = new LoginPage();
        loginPage.openPage();
    }

    @Test
    @DisplayName("Успешный логин с валидными данными")
    void successfulLoginTest() {
        loginPage
                .enterUsername("tomsmith")
                .enterPassword("SuperSecretPassword!")
                .clickLogin()
                .shouldSeeSuccessMessage()
                .shouldBeLoggedIn();
    }

    @Test
    @DisplayName("Успешный логин через единый метод login")
    void successfulLoginWithSingleMethodTest() {
        loginPage
                .login("tomsmith", "SuperSecretPassword!")
                .shouldSeeSuccessMessage()
                .shouldBeLoggedIn();
    }

    @Test
    @DisplayName("Неуспешный логин с неправильным паролем")
    void invalidPasswordTest() {
        loginPage
                .login("tomsmith", "wrongpassword")
                .shouldSeeErrorMessage();
    }

    @Test
    @DisplayName("Неуспешный логин с пустым логином")
    void emptyUsernameTest() {
        loginPage
                .login("", "SuperSecretPassword!")
                .shouldSeeInvalidUsernameMessage();
    }

    @Test
    @DisplayName("Неуспешный логин с пустым паролем")
    void emptyPasswordTest() {
        loginPage
                .login("tomsmith", "")
                .shouldSeeInvalidPasswordMessage();
    }

    @Test
    @DisplayName("Закрытие flash-сообщения")
    void closeFlashMessageTest() {
        loginPage
                .login("tomsmith", "wrongpassword")
                .shouldSeeErrorMessage()
                .closeMessage()
                .shouldBeOpen();  // остались на странице логина
    }

}
