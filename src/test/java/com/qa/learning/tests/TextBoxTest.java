package com.qa.learning.tests;

import com.microsoft.playwright.*;
import com.qa.learning.pages.TextBoxPage;

import io.qameta.allure.Allure;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.io.ByteArrayInputStream;

public class TextBoxTest {

    // Logger for this test class
    private static final Logger log = LogManager.getLogger(TextBoxTest.class);

    static Playwright playwright;
    static Browser browser;
    Page page;
    TextBoxPage textBoxPage;

    @BeforeAll
    static void launchBrowser() {
        log.info("=== Starting TextBoxTest suite ===");
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false));
        log.info("Browser launched successfully");
    }

    @BeforeEach
    void setUp() {
        log.info("Creating new page and navigating to TextBox");
        page = browser.newPage();
        textBoxPage = new TextBoxPage(page);
        textBoxPage.navigate();
    }

    // Helper method - defined once, used by all tests
    // Captures screenshot and attaches directly to Allure report
    private void captureScreenshot(String name) {
        byte[] screenshot = page.screenshot(
                new Page.ScreenshotOptions().setFullPage(true));
        Allure.addAttachment(name, "image/png",
                new ByteArrayInputStream(screenshot), "png");
        log.info("Screenshot attached to Allure: {}", name);
    }

    @Test
    @DisplayName("Form submits and displays entered values")
    void testTextBoxForm() {
        log.info("Running test: testTextBoxForm");

        textBoxPage.fillForm(
                "Susan Barrows",
                "susan@test.com",
                "123 Main St Phoenix AZ",
                "456 Other St Phoenix AZ");

        log.info("Asserting output name is correct");
        assertThat(page.locator("#output #name")).containsText("Susan Barrows");

        log.info("Asserting output email is correct");
        assertThat(page.locator("#output #email")).containsText("susan@test.com");

        log.info("✅ Test passed: {}", textBoxPage.getOutputName());
    }

    @AfterEach
    void closePage() {
        log.info("Closing page after test");
        page.close();
    }

    @AfterAll
    static void closeBrowser() {
        log.info("=== TextBoxTest suite complete - closing browser ===");
        browser.close();
        playwright.close();
    }
}