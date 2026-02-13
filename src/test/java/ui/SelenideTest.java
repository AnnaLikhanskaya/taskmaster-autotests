package ui;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

@DisplayName("UI тесты на Selenide")
public class SelenideTest {

    @BeforeAll
    static void setUp() {
        Configuration.browser = "chrome";
        Configuration.headless = false;
        Configuration.timeout = 10000;  // ждём 10 секунд
        Configuration.baseUrl = "https://the-internet.herokuapp.com";

        SelenideLogger.addListener("AllureSelenide",
                new AllureSelenide().screenshots(true).savePageSource(true));
    }

    // ============= ТЕСТ 1-2: Логин (уже есть) =============

    // ============= ТЕСТ 3: Чекбоксы =============
    @Test
    @DisplayName("Checkboxes: отметить и снять галочку")
    void checkboxesTest() {
        open("/checkboxes");

        SelenideElement firstCheckbox = $$("input[type='checkbox']").first();
        SelenideElement secondCheckbox = $$("input[type='checkbox']").last();

        firstCheckbox.shouldNotBe(checked);
        secondCheckbox.shouldBe(checked);

        firstCheckbox.click();
        firstCheckbox.shouldBe(checked);

        secondCheckbox.click();
        secondCheckbox.shouldNotBe(checked);

        secondCheckbox.click();
        secondCheckbox.shouldBe(checked);
    }

    // ============= ТЕСТ 4: Выпадающий список =============
    @Test
    @DisplayName("Dropdown: выбор значения из списка")
    void dropdownTest() {
        open("/dropdown");

        SelenideElement dropdown = $("#dropdown");

        dropdown.shouldHave(text("Please select an option"));

        dropdown.selectOption("Option 1");
        dropdown.shouldHave(text("Option 1"));

        dropdown.selectOption("Option 2");
        dropdown.shouldHave(text("Option 2"));

        dropdown.selectOption(1);
        dropdown.shouldHave(text("Option 1"));
    }

    // ============= ТЕСТ 5: Dynamic Controls (Checkbox) =============
    @Test
    @DisplayName("Dynamic Controls: удалить и добавить чекбокс")
    void dynamicControlsCheckboxTest() {
        open("/dynamic_controls");

        SelenideElement checkbox = $("#checkbox");
        checkbox.shouldBe(visible);

        $("button[onclick='swapCheckbox()']").click();

        $("#message").shouldHave(text("It's gone!"));
        checkbox.shouldNot(exist);

        $("button[onclick='swapCheckbox()']").click();

        $("#message").shouldHave(text("It's back!"));
        checkbox = $("#checkbox");
        checkbox.shouldBe(visible);
    }

    // ============= ТЕСТ 6: Dynamic Controls (Input) =============
    @Test
    @DisplayName("Dynamic Controls: включить/выключить поле ввода")
    void dynamicControlsInputTest() {
        open("/dynamic_controls");

        SelenideElement input = $("#input-example input");

        input.shouldBe(disabled);

        $("button[onclick='swapInput()']").click();

        $("#message").shouldHave(text("It's enabled!"));
        input.shouldBe(enabled);

        input.setValue("Hello, Selenide!");
        input.shouldHave(value("Hello, Selenide!"));
    }

    // ============= ТЕСТ 7: Dynamic Loading =============
    @Test
    @DisplayName("Dynamic Loading: элемент появляется после загрузки")
    void dynamicLoadingTest() {
        open("/dynamic_loading/1");

        $("button").click();

        // Ждём появления текста (явно указываем таймаут)
        $("#finish h4").shouldHave(text("Hello World!"), Duration.ofSeconds(10));
        $("#finish h4").shouldBe(visible);
    }
}