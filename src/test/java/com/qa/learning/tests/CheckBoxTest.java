package com.qa.learning.tests;

import com.microsoft.playwright.*;
import com.qa.learning.pages.CheckBoxPage;
import io.qameta.allure.Allure;
import java.io.ByteArrayInputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckBoxTest {

    private static final Logger log = LogManager.getLogger(CheckBoxTest.class);

    static Playwright playwright;
    static Browser browser;
    Page page;
    CheckBoxPage checkBoxPage;

    @BeforeAll
    static void launchBrowser() {
        log.info("=== Starting CheckBoxTest suite ===");
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false));
    }

    @BeforeEach
    void setUp() {
        log.info("Setting up page for CheckBoxTest");
        page = browser.newPage();
        checkBoxPage = new CheckBoxPage(page);
        checkBoxPage.navigate();
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
    @DisplayName("Selecting Home checkbox selects all children")
    void testSelectHomeCheckbox() {
        log.info("Running test: testSelectHomeCheckbox");

        captureScreenshot("checkbox_before");

        // Expand tree so all checkboxes are visible
        checkBoxPage.expandAll();

        // Click the Home checkbox
        checkBoxPage.selectHomeCheckbox();

        captureScreenshot("checkbox_after");

        // Assert result section is visible
        assertTrue(checkBoxPage.isResultDisplayed(),
                "Result should be displayed after selection");

        // Assert result contains expected text
        assertThat(page.locator(".display-result")).containsText("home");

        log.info("✅ Test passed - result: {}", checkBoxPage.getSelectedResult());
    }

    @Test
    @DisplayName("Result section not visible before any selection")
    void testNoResultBeforeSelection() {
        log.info("Running test: testNoResultBeforeSelection");

        captureScreenshot("checkbox_initial_state");

        boolean resultVisible = page.locator(".display-result").isVisible();
        assertTrue(!resultVisible, "Result should not be visible before selection");

        log.info("✅ Test passed - no result shown before selection");
    }

    @AfterEach
    void closePage(TestInfo testInfo) {
        String testName = testInfo.getTestMethod()
                .map(m -> m.getName())
                .orElse("unknown");
        captureScreenshot("end_" + testName);
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