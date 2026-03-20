package com.qa.learning.tests;

import org.junit.jupiter.api.Assertions;

// Import Playwright classes
import com.microsoft.playwright.*;

// Import page object for Radio Button page
import com.qa.learning.pages.RadioButtonPage;

// Import Log4j logger classes
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Import JUnit 5 annotations
import org.junit.jupiter.api.*;

/**
 * Test class for validating radio button functionality.
 * Uses Playwright for browser automation and JUnit 5 for test execution.
 */
public class RadioButtonTest {

    // Logger for this test class
    private static final Logger log = LogManager.getLogger(RadioButtonTest.class);

    // Shared Playwright instance for the full test class
    static Playwright playwright;

    // Shared browser instance for the full test class
    static Browser browser;

    // Page instance created fresh for each test
    Page page;

    // Page Object Model instance for Radio Button page
    RadioButtonPage radioButtonPage;

    /**
     * Runs once before all tests.
     * Launches Playwright and opens the browser.
     */
    @BeforeAll
    static void launchBrowser() {
        log.info("=== Starting RadioButtonTest suite ===");

        // Create Playwright instance
        playwright = Playwright.create();

        // Launch Chromium browser
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false));

        log.info("Browser launched successfully");
    }

    /**
     * Runs before each test.
     * Creates a new page and initializes the RadioButtonPage object.
     */
    @BeforeEach
    void setUp() {
        log.info("Creating new page and navigating to Radio Button page");

        // Open a new browser tab/page
        page = browser.newPage();

        // Initialize the page object
        radioButtonPage = new RadioButtonPage(page);

        // Navigate to the radio button page
        radioButtonPage.navigate();
    }

    /**
     * Test: Verify selecting "Yes" radio button displays correct result.
     */
    @Test
    @DisplayName("Verify selecting Yes radio button")
    void testSelectYesRadioButton() {
        log.info("Testing 'Yes' radio button");

        // 📸 BEFORE interaction
        radioButtonPage.takeScreenshot("before_select_yes");

        // Perform action
        radioButtonPage.selectYes();

        // 📸 AFTER interaction
        radioButtonPage.takeScreenshot("after_select_yes");

        // Assertion
        Assertions.assertEquals("Yes", radioButtonPage.getResultText().trim());

        log.info("Yes selection verified");
    }

    @Test
    @DisplayName("Verify selecting Impressive radio button")
    void testSelectImpressiveRadioButton() {
        log.info("Testing 'Impressive' radio button");

        radioButtonPage.selectImpressive();

        Assertions.assertEquals("Impressive", radioButtonPage.getResultText().trim());

        log.info("Impressive selection verified");
    }

    @Test
    @DisplayName("Verify 'No' radio button is disabled")
    void testNoRadioButtonIsDisabled() {
        log.info("Testing 'No' radio button disabled state");

        // Validate it's disabled (best practice)
        Assertions.assertTrue(radioButtonPage.isNoRadioDisabled());

        log.info("'No' radio button is correctly disabled");
    }

    /**
     * Runs after each test.
     * Closes the page to clean up resources.
     */
    @AfterEach
    void tearDown() {
        log.info("Closing page");
        page.close();
    }

    /**
     * Runs once after all tests.
     * Closes browser and Playwright instance.
     */
    @AfterAll
    static void closeBrowser() {
        log.info("=== Ending RadioButtonTest suite ===");

        browser.close();
        playwright.close();

        log.info("Browser and Playwright closed successfully");
    }
}