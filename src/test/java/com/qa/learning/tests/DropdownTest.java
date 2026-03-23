package com.qa.learning.tests;

import com.microsoft.playwright.*;
import com.qa.learning.pages.DropdownPage;
import io.qameta.allure.*;
import java.io.ByteArrayInputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.tools.picocli.CommandLine.Parameters;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@Epic("DemoQA - Elements")
@Feature("Dropdown")
public class DropdownTest {

    private static final Logger log = LogManager.getLogger(DropdownTest.class);

    static Playwright playwright;
    static Browser browser;
    Page page;
    DropdownPage dropdownPage;

    @BeforeAll
    static void launchBrowser() {
        log.info("=== Starting DropdownTest suite ===");
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false));
    }

    @BeforeEach
    void setUp() {
        log.info("Setting up page for DropdownTest");
        page = browser.newPage();
        dropdownPage = new DropdownPage(page);
        dropdownPage.navigate();
    }

    // Helper - captures screenshot and attaches to Allure report
    @SuppressWarnings("unused")
    private void captureScreenshot(String name) {
        byte[] screenshot = page.screenshot(
                new Page.ScreenshotOptions().setFullPage(true));
        Allure.addAttachment(name, "image/png",
                new ByteArrayInputStream(screenshot), "png");
        log.info("Screenshot attached to Allure: {}", name);
    }

    @ParameterizedTest
    @DisplayName("Select multiple options from old style select menu")
    @CsvSource({
            "1, Blue",
            "2, Green",
            "3, Yellow",
            "4, Purple"
    })
    void testSelectMultipleOldMenuOptions(String value, String label) {
        log.info("Testing selection of: {} (value={})", label, value);

        dropdownPage.selectOldMenuOption(value);

        String selected = dropdownPage.getOldMenuSelectedValue();
        log.info("Selected value returned: {}", selected);

        assertEquals(value, selected, "Expected value '" + value + "' for " + label);
    }

    @Test
    @DisplayName("Select Group 1 Option 1 from React Select dropdown")
    void testSelectReactSelectOption() {
        log.info("Running test: testSelectReactDropdownOption");

        dropdownPage.selectReactSelectValue("0-0");

        String selected = dropdownPage.getReactDropdownSelectedText();
        log.info("Selected text was: {}", selected);

        assertEquals("Group 1, option 1", selected, "Expected Group 1, option 1 to be selected");
    }

    @AfterEach
    void closePage(TestInfo testInfo) {
        String testName = testInfo.getTestMethod()
                .map(m -> m.getName())
                .orElse("unknown");
        dropdownPage.takeScreenshot("end_" + testName);
        log.info("Closing page after: {}", testInfo.getDisplayName());
        page.close();
    }

    @AfterAll
    static void closeBrowser() {
        log.info("=== DropdownTest suite complete ===");
        browser.close();
        playwright.close();
    }
}