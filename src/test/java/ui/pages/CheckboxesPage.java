package ui.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.checked;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class CheckboxesPage extends BasePage {

    // ============ ЛОКАТОРЫ ============
    private final ElementsCollection checkboxes = $$("input[type='checkbox']");
    private final SelenideElement firstCheckbox = checkboxes.first();
    private final SelenideElement secondCheckbox = checkboxes.last();
    private final SelenideElement heading = $("h3");

    // ============ ДЕЙСТВИЯ ============

    @Step("Открыть страницу с чекбоксами")
    public CheckboxesPage openPage() {
        open("/checkboxes");
        shouldBeOpen();
        return this;
    }

    @Step("Кликнуть на первый чекбокс")
    public CheckboxesPage clickFirstCheckbox() {
        firstCheckbox.click();
        return this;
    }

    @Step("Кликнуть на второй чекбокс")
    public CheckboxesPage clickSecondCheckbox() {
        secondCheckbox.click();
        return this;
    }

    // ============ ПРОВЕРКИ ============

    @Override
    @Step("Проверить, что страница с чекбоксами открылась")
    public void shouldBeOpen() {
        heading.shouldHave(text("Checkboxes"));
        checkboxes.shouldHave(size(2));
    }

    @Step("Проверить, что первый чекбокс выбран")
    public CheckboxesPage firstCheckboxShouldBeChecked() {
        firstCheckbox.shouldBe(checked);
        return this;
    }

    @Step("Проверить, что первый чекбокс не выбран")
    public CheckboxesPage firstCheckboxShouldBeUnchecked() {
        firstCheckbox.shouldNotBe(checked);
        return this;
    }

    @Step("Проверить, что второй чекбокс выбран")
    public CheckboxesPage secondCheckboxShouldBeChecked() {
        secondCheckbox.shouldBe(checked);
        return this;
    }

    @Step("Проверить, что второй чекбокс не выбран")
    public CheckboxesPage secondCheckboxShouldBeUnchecked() {
        secondCheckbox.shouldNotBe(checked);
        return this;
    }

    @Step("Проверить состояние по умолчанию")
    public CheckboxesPage shouldHaveDefaultState() {
        firstCheckboxShouldBeUnchecked();
        secondCheckboxShouldBeChecked();
        return this;
    }
}

