package ui.pages;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.webdriver;

/**
 * Базовый класс для всех страниц.
 * Содержит общую настройку и общие методы.
 */
public abstract class BasePage {

    // Конструктор - вызывается при создании страницы
    public BasePage() {

    }

    /**
     * Проверяет, открыта ли страница
     */
    protected boolean isPageOpen() {
        return Configuration.baseUrl.equals(webdriver().driver().url());
    }

    /**
     * Настройка для ВСЕХ тестов (вызывается 1 раз)
     */
    @BeforeAll
    public static void setUp() {
        Configuration.browser = "chrome";
        Configuration.headless = false;
        Configuration.timeout = 10000;
        Configuration.baseUrl = "https://the-internet.herokuapp.com";

        SelenideLogger.addListener("AllureSelenide",
                new AllureSelenide().screenshots(true).savePageSource(true));
    }

    /**
     * Общий метод для проверки заголовка страницы
     */
    public abstract void shouldBeOpen();
}