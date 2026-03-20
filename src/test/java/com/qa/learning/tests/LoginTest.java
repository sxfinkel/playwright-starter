package com.qa.learning.tests;

import com.microsoft.playwright.*;
import com.qa.learning.pages.LoginPage;
import org.junit.jupiter.api.*;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

// LoginTest contains all test scenarios for the login page
// Notice: no locators here - the page object handles all of that
public class LoginTest {

    // These are shared across all tests in this class
    static Playwright playwright; // Playwright instance - created once
    static Browser browser; // Browser instance - created once
    Page page; // Fresh page for each test
    LoginPage loginPage; // Our page object

    // Runs ONCE before all tests - launches the browser
    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(true) // true = no visible browser
        );
    }

    // Runs before EACH test - creates a fresh page and navigates to login
    @BeforeEach
    void setUp() {
        page = browser.newPage(); // fresh page = no shared state between tests
        loginPage = new LoginPage(page); // create the page object with our page
        loginPage.navigate(); // go to the login URL
    }

    // Test 1 - verify wrong credentials show an error message
    @Test
    @DisplayName("Invalid login shows error message")
    void testInvalidLogin() {
        loginPage.login("wronguser", "wrongpass"); // attempt bad login

        // Assert the error banner contains the expected text
        assertThat(page.locator("#flash")).containsText("Your username is invalid!");
        System.out.println("✅ Error message: " + loginPage.getErrorMessage());
    }

    // Test 2 - verify correct credentials land on the secure page
    @Test
    @DisplayName("Valid login reaches secure area")
    void testValidLogin() {
        loginPage.login("tomsmith", "SuperSecretPassword!"); // known valid credentials

        // Assert the URL changed to the secure area after login
        assertThat(page).hasURL("https://the-internet.herokuapp.com/secure");
        System.out.println("✅ Logged in successfully");
    }

    // Runs after EACH test - closes the page to free up memory
    @AfterEach
    void closePage() {
        page.close();
    }

    // Runs ONCE after all tests - shuts down browser and Playwright
    @AfterAll
    static void closeBrowser() {
        browser.close();
        playwright.close();
    }
}