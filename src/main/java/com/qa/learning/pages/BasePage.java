package com.qa.learning.pages;

import com.microsoft.playwright.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// BasePage is the parent class for all page objects
// All page objects inherit screenshot capability and logging from here
public class BasePage {

    protected static final Logger log = LogManager.getLogger(BasePage.class);
    protected Page page;

    public BasePage(Page page) {
        this.page = page;
    }

    // Returns current page title
    public String getTitle() {
        String title = page.title();
        log.info("Current page title: {}", title);
        return title;
    }

    // Navigate to any URL
    public void navigateTo(String url) {
        log.info("Navigating to: {}", url);
        page.navigate(url);
    }

    // Takes a screenshot and saves it to target/screenshots/
    // fileName = a meaningful name like "login-success" or "checkbox-selected"
    public void takeScreenshot(String fileName) {
        // Generate timestamp so screenshots never overwrite each other
        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));

        // Build the full file path
        String path = "target/screenshots/" + fileName + "_" + timestamp + ".png";

        log.info("Taking screenshot: {}", path);

        // Playwright built-in screenshot method
        page.screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get(path))
                .setFullPage(true) // captures full page not just visible area
        );

        log.info("Screenshot saved: {}", path);
    }

    // Takes a screenshot automatically on test failure
    // Call this in your @AfterEach when a test fails
    public void takeScreenshotOnFailure(String testName) {
        log.warn("Test failed - capturing failure screenshot for: {}", testName);
        takeScreenshot("FAILED_" + testName);
    }
}