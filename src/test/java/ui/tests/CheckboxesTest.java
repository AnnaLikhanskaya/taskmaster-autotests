package ui.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ui.pages.CheckboxesPage;

import static ui.pages.BasePage.setUp;

@DisplayName("Тесты страницы с чекбоксами")
public class CheckboxesTest {

    private CheckboxesPage checkboxesPage;

    @BeforeEach
    void init() {
        setUp();
        checkboxesPage = new CheckboxesPage();
        checkboxesPage.openPage();
    }

    @Test
    @DisplayName("Проверка состояния по умолчанию")
    void defaultStateTest() {
        checkboxesPage.shouldHaveDefaultState();
    }

    @Test
    @DisplayName("Клик по первому чекбоксу")
    void clickFirstCheckboxTest() {
        checkboxesPage
                .clickFirstCheckbox()
                .firstCheckboxShouldBeChecked()
                .secondCheckboxShouldBeChecked();  // второй не изменился
    }

    @Test
    @DisplayName("Клик по второму чекбоксу")
    void clickSecondCheckboxTest() {
        checkboxesPage
                .clickSecondCheckbox()
                .firstCheckboxShouldBeUnchecked()
                .secondCheckboxShouldBeUnchecked();
    }

    @Test
    @DisplayName("Клик по обоим чекбоксам")
    void clickBothCheckboxesTest() {
        checkboxesPage
                .clickFirstCheckbox()
                .clickSecondCheckbox()
                .firstCheckboxShouldBeChecked()
                .secondCheckboxShouldBeUnchecked();
    }
}