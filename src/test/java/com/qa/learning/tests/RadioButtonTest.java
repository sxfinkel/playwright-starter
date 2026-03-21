package com.qa.learning.tests;

import com.microsoft.playwright.*;
import com.qa.learning.pages.RadioButtonPage;
import io.qameta.allure.*;
import java.io.ByteArrayInputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;

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
    @DisplayName("Verify selecting Yes radio button")
    @Story("User selects Yes radio button")
    @Severity(SeverityLevel.CRITICAL)
    void testSelectYesRadioButton() {
        log.info("Testing 'Yes' radio button");
        captureScreenshot("yes_radio_before");
        radioButtonPage.selectYes();
        captureScreenshot("yes_radio_after");
        Assertions.assertEquals("Yes", radioButtonPage.getResultText().trim());
        log.info("Yes selection verified");
    }

    @Test
    @DisplayName("Verify selecting Impressive radio button")
    @Story("User selects Impressive radio button")
    @Severity(SeverityLevel.NORMAL)
    void testSelectImpressiveRadioButton() {
        log.info("Testing 'Impressive' radio button");
        captureScreenshot("impressive_radio_before");
        radioButtonPage.selectImpressive();
        captureScreenshot("impressive_radio_after");
        Assertions.assertEquals("Impressive", radioButtonPage.getResultText().trim());
        log.info("Impressive selection verified");
    }

    @Test
    @DisplayName("Verify No radio button is disabled")
    @Story("No radio button is disabled by default")
    @Severity(SeverityLevel.MINOR)
    void testNoRadioButtonIsDisabled() {
        log.info("Testing 'No' radio button disabled state");
        captureScreenshot("no_radio_disabled_state");
        Assertions.assertTrue(radioButtonPage.isNoRadioDisabled());
        log.info("'No' radio button is correctly disabled");
    }

    @AfterEach
    void tearDown() {
        log.info("Closing page after test");
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