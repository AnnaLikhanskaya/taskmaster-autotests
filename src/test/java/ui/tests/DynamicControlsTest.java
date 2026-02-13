package ui.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ui.pages.DynamicControlsPage;

import static ui.pages.BasePage.setUp;

@DisplayName("Тесты страницы Dynamic Controls")
public class DynamicControlsTest {

    private DynamicControlsPage dynamicControlsPage;

    @BeforeEach
    void init() {
        setUp();
        dynamicControlsPage = new DynamicControlsPage();
        dynamicControlsPage.openPage();
    }

    @Test
    @DisplayName("Удаление и добавление чекбокса")
    void removeAndAddCheckboxTest() {
        dynamicControlsPage
                .checkboxShouldBeVisible()
                .clickRemoveAddButton()
                .shouldHaveMessage("It's gone!")
                .checkboxShouldNotExist()
                .clickRemoveAddButton()
                .shouldHaveMessage("It's back!")
                .checkboxShouldBeVisible();
    }

    @Test
    @DisplayName("Включение и отключение поля ввода")
    void enableAndDisableInputTest() {
        dynamicControlsPage
                .inputShouldBeDisabled()
                .clickEnableDisableButton()
                .shouldHaveMessage("It's enabled!")
                .inputShouldBeEnabled()
                .enterText("Selenide test")
                .inputShouldHaveValue("Selenide test")
                .clickEnableDisableButton()
                .shouldHaveMessage("It's disabled!")
                .inputShouldBeDisabled();
    }
}