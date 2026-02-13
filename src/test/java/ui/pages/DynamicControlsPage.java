package ui.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

/**
 * Page Object для страницы с динамическими элементами:
 * https://the-internet.herokuapp.com/dynamic_controls
 */
public class DynamicControlsPage extends BasePage {

    // ============ ЛОКАТОРЫ ============
    private final SelenideElement checkbox = $("#checkbox");
    private final SelenideElement removeAddButton = $("button[onclick='swapCheckbox()']");
    private final SelenideElement message = $("#message");

    private final SelenideElement input = $("#input-example input");
    private final SelenideElement enableDisableButton = $("button[onclick='swapInput()']");

    // ============ ДЕЙСТВИЯ ============

    @Step("Открыть страницу Dynamic Controls")
    public DynamicControlsPage openPage() {
        open("/dynamic_controls");
        shouldBeOpen();
        return this;
    }

    @Step("Нажать кнопку Remove/Add")
    public DynamicControlsPage clickRemoveAddButton() {
        removeAddButton.click();
        return this;
    }

    @Step("Нажать кнопку Enable/Disable")
    public DynamicControlsPage clickEnableDisableButton() {
        enableDisableButton.click();
        return this;
    }

    @Step("Ввести текст в поле: {text}")
    public DynamicControlsPage enterText(String text) {
        input.setValue(text);
        return this;
    }

    // ============ ПРОВЕРКИ ============

    @Override
    @Step("Проверить, что страница Dynamic Controls открылась")
    public void shouldBeOpen() {
        $("h4").shouldHave(text("Dynamic Controls"));
    }

    @Step("Проверить, что чекбокс виден")
    public DynamicControlsPage checkboxShouldBeVisible() {
        checkbox.shouldBe(visible);
        return this;
    }

    @Step("Проверить, что чекбокс НЕ виден (удалён)")
    public DynamicControlsPage checkboxShouldNotExist() {
        checkbox.shouldNot(exist);
        return this;
    }

    @Step("Проверить сообщение: {expectedText}")
    public DynamicControlsPage shouldHaveMessage(String expectedText) {
        message.shouldHave(text(expectedText), Duration.ofSeconds(10));
        return this;
    }

    @Step("Проверить, что поле ввода доступно")
    public DynamicControlsPage inputShouldBeEnabled() {
        input.shouldBe(enabled);
        return this;
    }

    @Step("Проверить, что поле ввода НЕ доступно")
    public DynamicControlsPage inputShouldBeDisabled() {
        input.shouldBe(disabled);
        return this;
    }

    @Step("Проверить значение в поле: {expectedValue}")
    public DynamicControlsPage inputShouldHaveValue(String expectedValue) {
        input.shouldHave(value(expectedValue));
        return this;
    }
}