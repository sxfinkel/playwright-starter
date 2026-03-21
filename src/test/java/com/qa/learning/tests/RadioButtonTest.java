package com.qa.learning.tests;

import com.microsoft.playwright.*;
import com.qa.learning.pages.RadioButtonPage;
import io.qameta.allure.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;

// Groups all radio button tests under this Epic in Allure report
@Epic("DemoQA - Elements")
@Feature("Radio Button")
public class RadioButtonTest {

    private static final Logger log = LogManager.getLogger(RadioButtonTest.class);

    static Playwright playwright;
    static Browser browser;
    Page page;
    RadioButtonPage radioButtonPage;

    @BeforeAll
    static void launchBrowser() {
        log.info("=== Starting RadioButtonTest suite ===");
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false));
        log.info("Browser launched successfully");
    }

    @BeforeEach
    void setUp() {
        log.info("Creating new page and navigating to Radio Button page");
        page = browser.newPage();
        radioButtonPage = new RadioButtonPage(page);
        radioButtonPage.navigate();
    }

    @Test
    @DisplayName("Verify selecting Yes radio button")
    @Story("User selects Yes radio button")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifies that selecting Yes displays correct result text")
    void testSelectYesRadioButton() {
        log.info("Testing 'Yes' radio button");

        // Screenshot before interaction
        radioButtonPage.takeScreenshot("yes_radio", "before");

        radioButtonPage.selectYes();

        // Screenshot after interaction
        radioButtonPage.takeScreenshot("yes_radio", "after");

        Assertions.assertEquals("Yes", radioButtonPage.getResultText().trim());
        log.info("Yes selection verified");
    }

    @Test
    @DisplayName("Verify selecting Impressive radio button")
    @Story("User selects Impressive radio button")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies that selecting Impressive displays correct result text")
    void testSelectImpressiveRadioButton() {
        log.info("Testing 'Impressive' radio button");

        radioButtonPage.takeScreenshot("impressive_radio", "before");

        radioButtonPage.selectImpressive();

        radioButtonPage.takeScreenshot("impressive_radio", "after");

        Assertions.assertEquals("Impressive", radioButtonPage.getResultText().trim());
        log.info("Impressive selection verified");
    }

    @Test
    @DisplayName("Verify No radio button is disabled")
    @Story("No radio button is disabled by default")
    @Severity(SeverityLevel.MINOR)
    @Description("Verifies the No radio button cannot be selected")
    void testNoRadioButtonIsDisabled() {
        log.info("Testing 'No' radio button disabled state");

        radioButtonPage.takeScreenshot("no_radio", "disabled_state");

        Assertions.assertTrue(radioButtonPage.isNoRadioDisabled());
        log.info("'No' radio button is correctly disabled");
    }

    @AfterEach
    void tearDown() {
        log.info("Closing page");
        page.close();
    }

    @AfterAll
    static void closeBrowser() {
        log.info("=== Ending RadioButtonTest suite ===");
        browser.close();
        playwright.close();
        log.info("Browser and Playwright closed successfully");
    }
}