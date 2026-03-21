package com.qa.learning.tests;

import com.microsoft.playwright.*;
import com.qa.learning.pages.AlertsPage;
import io.qameta.allure.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@Epic("DemoQA - Alerts")
@Feature("Browser Alerts")
public class AlertsTest {

    private static final Logger log = LogManager.getLogger(AlertsTest.class);

    static Playwright playwright;
    static Browser browser;
    Page page;
    AlertsPage alertsPage;

    @BeforeAll
    static void launchBrowser() {
        log.info("=== Starting AlertsTest suite ===");
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false));
        log.info("Browser launched successfully");
    }

    @BeforeEach
    void setUp() {
        log.info("Creating new page and navigating to Alerts page");
        page = browser.newPage();
        alertsPage = new AlertsPage(page);
        alertsPage.navigate();
    }

    @Test
    @DisplayName("Simple alert can be accepted")
    @Story("User accepts a simple alert")
    @Severity(SeverityLevel.NORMAL)
    void testSimpleAlert() {
        log.info("Running test: testSimpleAlert");

        // Screenshot before triggering alert
        alertsPage.takeScreenshot("simple_alert", "before");

        // Accept the alert
        alertsPage.clickAlertAndAccept();

        // Screenshot after alert dismissed
        alertsPage.takeScreenshot("simple_alert", "after");

        log.info("✅ Simple alert accepted successfully");
    }

    @Test
    @DisplayName("Confirm alert accepted shows OK result")
    @Story("User clicks OK on confirm box")
    @Severity(SeverityLevel.NORMAL)
    void testConfirmAccept() {
        log.info("Running test: testConfirmAccept");

        alertsPage.takeScreenshot("confirm_alert", "before");

        alertsPage.clickConfirmAndAccept();

        alertsPage.takeScreenshot("confirm_alert", "after_accept");

        // Assert result text shows OK was clicked
        assertTrue(alertsPage.getConfirmResult().contains("Ok"),
                "Result should show Ok was selected");

        log.info("✅ Confirm accepted - result verified");
    }

    @Test
    @DisplayName("Confirm alert dismissed shows Cancel result")
    @Story("User clicks Cancel on confirm box")
    @Severity(SeverityLevel.NORMAL)
    void testConfirmDismiss() {
        log.info("Running test: testConfirmDismiss");

        alertsPage.clickConfirmAndDismiss();

        alertsPage.takeScreenshot("confirm_alert", "after_dismiss");

        // Assert result text shows Cancel was clicked
        assertTrue(alertsPage.getConfirmResult().contains("Cancel"),
                "Result should show Cancel was selected");

        log.info("✅ Confirm dismissed - result verified");
    }

    @Test
    @DisplayName("Prompt alert accepts typed text")
    @Story("User types text into prompt box")
    @Severity(SeverityLevel.NORMAL)
    void testPromptInput() {
        log.info("Running test: testPromptInput");

        alertsPage.takeScreenshot("prompt_alert", "before");

        // Type a name into the prompt
        alertsPage.clickPromptAndType("Susan");

        alertsPage.takeScreenshot("prompt_alert", "after");

        // Assert result contains the text we typed
        assertTrue(alertsPage.getPromptResult().contains("Susan"),
                "Result should contain the typed text");

        log.info("✅ Prompt input verified");
    }

    @AfterEach
    void tearDown() {
        log.info("Closing page after test");
        page.close();
    }

    @AfterAll
    static void closeBrowser() {
        log.info("=== AlertsTest suite complete ===");
        browser.close();
        playwright.close();
    }
}