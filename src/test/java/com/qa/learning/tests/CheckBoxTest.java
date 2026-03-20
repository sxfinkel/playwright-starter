package com.qa.learning.tests;

import com.microsoft.playwright.*;
import com.qa.learning.pages.CheckBoxPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckBoxTest {

    private static final Logger log = LogManager.getLogger(CheckBoxTest.class);

    static Playwright playwright;
    static Browser browser;
    Page page;
    CheckBoxPage checkBoxPage;

    // TestInfo gives us the current test name for screenshot naming
    TestInfo testInfo;

    @BeforeAll
    static void launchBrowser() {
        log.info("=== Starting CheckBoxTest suite ===");
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false));
    }

    @BeforeEach
    void setUp(TestInfo testInfo) {
        // Store test info so we can use test name in screenshots
        this.testInfo = testInfo;
        log.info("Setting up page for: {}", testInfo.getDisplayName());
        page = browser.newPage();
        checkBoxPage = new CheckBoxPage(page);
        checkBoxPage.navigate();
    }

    @Test
    @DisplayName("Selecting Home checkbox selects all children")
    void testSelectHomeCheckbox() {
        log.info("Running test: testSelectHomeCheckbox");

        checkBoxPage.selectHomeCheckbox();

        // Take screenshot after selecting checkbox
        checkBoxPage.takeScreenshot("checkbox-home-selected");

        assertTrue(checkBoxPage.isResultDisplayed(),
                "Result should be displayed after selection");
        assertThat(page.locator(".display-result")).containsText("home");

        log.info("✅ Test passed - result: {}", checkBoxPage.getSelectedResult());
    }

    @Test
    @DisplayName("Result section not visible before any selection")
    void testNoResultBeforeSelection() {
        log.info("Running test: testNoResultBeforeSelection");

        // Take screenshot of initial state before any interaction
        checkBoxPage.takeScreenshot("checkbox-initial-state");

        boolean resultVisible = page.locator(".display-result").isVisible();
        assertTrue(!resultVisible, "Result should not be visible before selection");

        log.info("✅ Test passed - no result shown before selection");
    }

    // Runs after each test - takes screenshot if test failed
    @AfterEach
    void closePage(TestInfo testInfo) {
        // Get the simple method name for the screenshot file
        String testName = testInfo.getTestMethod()
                .map(m -> m.getName())
                .orElse("unknown");

        // Always take a final screenshot at end of each test
        checkBoxPage.takeScreenshot("end_" + testName);

        log.info("Closing page after: {}", testInfo.getDisplayName());
        page.close();
    }

    @AfterAll
    static void closeBrowser() {
        log.info("=== CheckBoxTest suite complete ===");
        browser.close();
        playwright.close();
    }
}