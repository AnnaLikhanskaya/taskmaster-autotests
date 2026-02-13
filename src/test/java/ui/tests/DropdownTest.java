package ui.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ui.pages.DropdownPage;

import static ui.pages.BasePage.setUp;

@DisplayName("Тесты выпадающего списка")
public class DropdownTest {

    private DropdownPage dropdownPage;

    @BeforeEach
    void init() {
        setUp();
        dropdownPage = new DropdownPage();
        dropdownPage.openPage();
    }

    @Test
    @DisplayName("Проверка состояния по умолчанию")
    void defaultStateTest() {
        dropdownPage.shouldHaveDefaultOption();
    }

    @Test
    @DisplayName("Выбор опции по видимому тексту")
    void selectByVisibleTextTest() {
        dropdownPage
                .selectByVisibleText("Option 1")
                .shouldHaveSelectedOption("Option 1")
                .shouldHaveSelectedValue("1");

        dropdownPage
                .selectByVisibleText("Option 2")
                .shouldHaveSelectedOption("Option 2")
                .shouldHaveSelectedValue("2");
    }

    @Test
    @DisplayName("Выбор опции по значению (value)")
    void selectByValueTest() {
        dropdownPage
                .selectByValue("1")
                .shouldHaveSelectedOption("Option 1")
                .shouldHaveSelectedValue("1");

        dropdownPage
                .selectByValue("2")
                .shouldHaveSelectedOption("Option 2")
                .shouldHaveSelectedValue("2");
    }

    @Test
    @DisplayName("Выбор опции по индексу")
    void selectByIndexTest() {
        // Индексы: 0 - первый элемент, 1 - Option 1, 2 - Option 2
        dropdownPage
                .selectByIndex(1)
                .shouldHaveSelectedOption("Option 1");

        dropdownPage
                .selectByIndex(2)
                .shouldHaveSelectedOption("Option 2");
    }

    @Test
    @DisplayName("Множественный выбор (проверка что выбирается последний)")
    void multipleSelectionTest() {
        dropdownPage
                .selectByVisibleText("Option 1")
                .shouldHaveSelectedOption("Option 1")
                .selectByVisibleText("Option 2")
                .shouldHaveSelectedOption("Option 2");  // должен переключиться
    }
}