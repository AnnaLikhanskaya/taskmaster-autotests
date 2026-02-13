package ui.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class LoginPage extends BasePage {

    // ============ ЛОКАТОРЫ (ВСЕ В ОДНОМ МЕСТЕ) ============
    private final SelenideElement usernameInput = $("#username");
    private final SelenideElement passwordInput = $("#password");
    private final SelenideElement loginButton = $("button[type='submit']");
    private final SelenideElement flashMessage = $("#flash");
    private final SelenideElement closeButton = $(".close");


    @Step("Открыть страницу логина")
    public LoginPage openPage() {
        open("/login");
        shouldBeOpen();
        return this;
    }

    @Step("Ввести логин: {username}")
    public LoginPage enterUsername(String username) {
        usernameInput.setValue(username);
        return this;
    }

    @Step("Ввести пароль")
    public LoginPage enterPassword(String password) {
        passwordInput.setValue(password);
        return this;
    }

    @Step("Нажать кнопку Login")
    public LoginPage clickLogin() {
        loginButton.click();
        return this;
    }

    @Step("Логин с пользователем {username}")
    public LoginPage login(String username, String password) {
        return enterUsername(username)
                .enterPassword(password)
                .clickLogin();
    }

    @Step("Закрыть сообщение")
    public LoginPage closeMessage() {
        if (closeButton.isDisplayed()) {
            closeButton.click();
            flashMessage.should(disappear);
        }
        return this;
    }

    // ============ ПРОВЕРКИ (ЧТО МОЖНО ПРОВЕРИТЬ) ============


    /**
     * Проверить, что страница логина открылась
     */
    @Override
    @Step("Проверить, что страница логина открылась")
    public void shouldBeOpen() {
        usernameInput.shouldBe(visible);
        passwordInput.shouldBe(visible);
        loginButton.shouldBe(visible);
    }

    /**
     * Проверить сообщение об успехе
     */
    @Step("Проверить сообщение об успешном логине")
    public LoginPage shouldSeeSuccessMessage() {
        flashMessage.shouldHave(text("You logged into a secure area!"));
        return this;
    }

    /**
     * Проверить сообщение об ошибке
     */
    @Step("Проверить сообщение об ошибке")
    public LoginPage shouldSeeErrorMessage() {
        flashMessage.shouldBe(visible);
        return this;
    }

    /**
     * Проверить, что пользователь залогинен (редирект на secure page)
     */
    @Step("Проверить, что пользователь залогинен")
    public void shouldBeLoggedIn() {
        $("#content").shouldHave(text("Secure Area"));
        $("h2").shouldHave(text("Secure Area"));
    }

    @Step("Проверить сообщение о неверном пароле")
    public LoginPage shouldSeeInvalidPasswordMessage() {
        flashMessage.shouldHave(text("Your password is invalid!"));
        return this;
    }

    @Step("Проверить сообщение о неверном логине")
    public LoginPage shouldSeeInvalidUsernameMessage() {
        flashMessage.shouldHave(text("Your username is invalid!"));
        return this;
    }
}