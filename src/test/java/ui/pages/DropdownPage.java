package ui.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.$$;

/**
 * Page Object для страницы с выпадающим списком:
 * https://the-internet.herokuapp.com/dropdown
 */
public class DropdownPage extends BasePage {

    // ============ ЛОКАТОРЫ ============
    private final SelenideElement dropdown = $("#dropdown");
    private final SelenideElement heading = $("h3");

    // ============ ДЕЙСТВИЯ ============

    @Step("Открыть страницу с выпадающим списком")
    public DropdownPage openPage() {
        open("/dropdown");
        shouldBeOpen();
        return this;
    }

    @Step("Выбрать опцию по видимому тексту: {optionText}")
    public DropdownPage selectByVisibleText(String optionText) {
        dropdown.selectOption(optionText);
        return this;
    }

    @Step("Выбрать опцию по значению (value): {optionValue}")
    public DropdownPage selectByValue(String optionValue) {
        dropdown.selectOptionByValue(optionValue);
        return this;
    }

    @Step("Выбрать опцию по индексу: {index}")
    public DropdownPage selectByIndex(int index) {
        dropdown.selectOption(index);
        return this;
    }

    // ============ ПРОВЕРКИ ============

    @Override
    @Step("Проверить, что страница с выпадающим списком открылась")
    public void shouldBeOpen() {
        heading.shouldHave(text("Dropdown List"));
        dropdown.shouldBe(visible);
    }

    @Step("Проверить, что выбрана опция: {expectedText}")
    public DropdownPage shouldHaveSelectedOption(String expectedText) {
        dropdown.shouldHave(text(expectedText));
        return this;
    }

    @Step("Проверить, что значение (value) выбранной опции: {expectedValue}")
    public DropdownPage shouldHaveSelectedValue(String expectedValue) {
        dropdown.shouldHave(value(expectedValue));
        return this;
    }

    @Step("Проверить, что выбрана опция по умолчанию")
    public DropdownPage shouldHaveDefaultOption() {
        dropdown.shouldHave(text("Please select an option"));
        dropdown.shouldHave(value(""));  // пустое значение
        return this;
    }
}