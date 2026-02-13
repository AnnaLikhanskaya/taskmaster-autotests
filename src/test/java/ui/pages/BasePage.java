package ui.pages;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.chrome.ChromeOptions;

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

        boolean isCI = Boolean.parseBoolean(System.getenv("CI"));
        Configuration.browser = "chrome";
        Configuration.headless = isCI;
        Configuration.timeout = 15000;
        Configuration.baseUrl = "https://the-internet.herokuapp.com";

        if (isCI) {
            Configuration.browserSize = "1920x1080";
            Configuration.browserCapabilities.setCapability(
                    "goog:chromeOptions",
                    new ChromeOptions().addArguments(
                            "--disable-dev-shm-usage",
                            "--no-sandbox",
                            "--disable-gpu",
                            "--window-size=1920,1080"
                    )
            );
        }

        if (!isCI) {
            SelenideLogger.addListener("AllureSelenide",
                    new AllureSelenide().screenshots(true).savePageSource(true));
        }

    }
}