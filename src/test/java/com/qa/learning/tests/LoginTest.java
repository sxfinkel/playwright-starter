package com.qa.learning.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.microsoft.playwright.*;
import com.qa.learning.pages.LoginPage;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.*;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import java.io.ByteArrayInputStream;

// LoginTest contains all test scenarios for the login page
public class LoginTest {

    // Logger for this test class
    private static final Logger log = LogManager.getLogger(LoginTest.class);

    static Playwright playwright;
    static Browser browser;
    Page page;
    LoginPage loginPage;

    @BeforeAll
    static void launchBrowser() {
        log.info("=== Starting LoginTest suite ===");
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(true));
        log.info("Browser launched successfully");
    }

    @BeforeEach
    void setUp() {
        log.info("Setting up page for LoginTest");
        page = browser.newPage();
        loginPage = new LoginPage(page);
        loginPage.navigate();
    }

    // Helper - captures screenshot and attaches to Allure report
    private void captureScreenshot(String name) {
        byte[] screenshot = page.screenshot(
                new Page.ScreenshotOptions().setFullPage(true));
        Allure.addAttachment(name, "image/png",
                new ByteArrayInputStream(screenshot), "png");
        log.info("Screenshot attached to Allure: {}", name);
    }

    @Test
    @DisplayName("Invalid login shows error message")
    void testInvalidLogin() {
        log.info("Running test: testInvalidLogin");
        captureScreenshot("invalid_login_before");
        loginPage.login("wronguser", "wrongpass");
        captureScreenshot("invalid_login_after");
        assertThat(page.locator("#flash")).containsText("Your username is invalid!");
        log.info("✅ Error message verified");
    }

    @Test
    @DisplayName("Valid login reaches secure area")
    void testValidLogin() {
        log.info("Running test: testValidLogin");
        captureScreenshot("valid_login_before");
        loginPage.login("tomsmith", "SuperSecretPassword!");
        captureScreenshot("valid_login_after");
        assertThat(page).hasURL("https://the-internet.herokuapp.com/secure");
        log.info("✅ Logged in successfully");
    }

    @AfterEach
    void closePage() {
        log.info("Closing page after test");
        page.close();
    }

    @AfterAll
    static void closeBrowser() {
        log.info("=== LoginTest suite complete ===");
        browser.close();
        playwright.close();
    }
}