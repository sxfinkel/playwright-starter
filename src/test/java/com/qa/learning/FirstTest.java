package com.qa.learning;

// Import Playwright core classes
import com.microsoft.playwright.*;

// Import JUnit 5 annotations for test lifecycle management
import org.junit.jupiter.api.*;

// Import Playwright assertion library
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

// Used for flexible pattern matching in assertions
import java.util.regex.Pattern;

public class FirstTest {

    // Shared Playwright instance for all tests (initialized once)
    static Playwright playwright;

    // Shared browser instance (launched once per test class)
    static Browser browser;

    // Page instance (created fresh for each test to ensure isolation)
    Page page;

    /**
     * Runs once before all tests.
     * Initializes Playwright and launches the browser in headless mode.
     */
    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();

        // Launch Chromium browser in headless mode (no UI)
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(true));
    }

    /**
     * Runs before each test.
     * Creates a new browser page to ensure test independence.
     */
    @BeforeEach
    void createPage() {
        page = browser.newPage();
    }

    /**
     * Test: Verify Playwright homepage title contains "Playwright"
     */
    @Test
    @DisplayName("Verify Playwright homepage title")
    void testPlaywrightTitle() {

        // Navigate to Playwright official website
        page.navigate("https://playwright.dev");

        // Assert page title contains the word "Playwright"
        assertThat(page).hasTitle(Pattern.compile("Playwright"));

        // Log title for debugging/visibility
        System.out.println("✅ Title: " + page.title());
    }

    /**
     * Test: Verify error message is displayed for invalid login attempt
     */
    @Test
    @DisplayName("Login with invalid credentials shows error")
    void testFailedLogin() {

        // Navigate to login page
        page.navigate("https://the-internet.herokuapp.com/login");

        // Enter invalid username
        page.locator("#username").fill("wronguser");

        // Enter invalid password
        page.locator("#password").fill("wrongpass");

        // Click login button
        page.locator("button[type='submit']").click();

        // Assert error message is displayed
        assertThat(page.locator("#flash")).containsText("Your username is invalid!");

        // Log success message
        System.out.println("✅ Error message verified");
    }

    /**
     * Runs after each test.
     * Closes the page to clean up resources.
     */
    @AfterEach
    void closePage() {
        page.close();
    }

    /**
     * Runs once after all tests.
     * Closes browser and Playwright instance.
     */
    @AfterAll
    static void closeBrowser() {
        browser.close();
        playwright.close();
    }
}