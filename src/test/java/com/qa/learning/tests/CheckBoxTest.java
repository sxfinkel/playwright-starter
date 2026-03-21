package com.qa.learning.tests;

import com.microsoft.playwright.*;
import com.qa.learning.pages.CheckBoxPage;
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

    @Test
    @DisplayName("Selecting Home checkbox selects all children")
    void testSelectHomeCheckbox() {
        log.info("Running test: testSelectHomeCheckbox");

        // Expand tree so all checkboxes are visible
        checkBoxPage.expandAll();

        // Click the Home checkbox
        checkBoxPage.selectHomeCheckbox();

        // Assert result section is visible
        assertTrue(checkBoxPage.isResultDisplayed(), "Result should be displayed after selection");

        // Assert result contains expected text
        assertThat(page.locator(".display-result")).containsText("home");

        log.info("✅ Test passed - result: {}", checkBoxPage.getSelectedResult());
    }

    @Test
    @DisplayName("Result section not visible before any selection")
    void testNoResultBeforeSelection() {
        log.info("Running test: testNoResultBeforeSelection");

        // Without clicking anything the result section should not exist
        boolean resultVisible = page.locator(".display-result").isVisible();
        assertTrue(!resultVisible, "Result should not be visible before any selection");

        log.info("✅ Test passed - no result shown before selection");
    }

    @AfterEach
    void closePage(TestInfo testInfo) {
        // Take end of test screenshot and attach to Allure
        String testName = testInfo.getTestMethod()
                .map(m -> m.getName())
                .orElse("unknown");
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