package com.qa.learning;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import java.util.regex.Pattern;

public class FirstTest {

    static Playwright playwright;
    static Browser browser;
    Page page;

    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(true));
    }

    @BeforeEach
    void createPage() {
        page = browser.newPage();
    }

    @Test
    @DisplayName("Verify Playwright homepage title")
    void testPlaywrightTitle() {
        page.navigate("https://playwright.dev");
        assertThat(page).hasTitle(Pattern.compile("Playwright"));
        System.out.println("✅ Title: " + page.title());
    }

    @AfterEach
    void closePage() {
        page.close();
    }

    @AfterAll
    static void closeBrowser() {
        browser.close();
        playwright.close();
    }
}