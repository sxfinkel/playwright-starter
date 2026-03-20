package com.qa.learning.pages;

import com.microsoft.playwright.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// BasePage is the parent class for all page objects
// All page objects inherit common functionality from here
public class BasePage {

    // Logger instance — every class that extends BasePage can use this
    protected static final Logger log = LogManager.getLogger(BasePage.class);

    // 'page' represents the browser page
    protected Page page;

    public BasePage(Page page) {
        this.page = page;
    }

    // Returns the current page title
    public String getTitle() {
        String title = page.title();
        log.info("Current page title: {}", title);
        return title;
    }

    // Navigates to a given URL
    public void navigateTo(String url) {
        log.info("Navigating to: {}", url);
        page.navigate(url);
    }

    // Original single argument version - keeps existing tests working
    public void takeScreenshot(String fileName) {
        takeScreenshot(fileName, "");
    }

    // New version with test name and step - used in RadioButtonTest
    public void takeScreenshot(String testName, String step) {
        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

        // If step is empty just use the name, otherwise combine both
        String name = step.isEmpty() ? testName : testName + "_" + step;
        String filePath = "target/screenshots/" + name + "_" + timestamp + ".png";

        log.info("Capturing screenshot: {}", filePath);

        page.screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get(filePath))
                .setFullPage(true));

        log.info("Screenshot saved: {}", filePath);
    }

    // Takes a screenshot automatically on failure
    public void takeScreenshotOnFailure(String testName) {
        log.warn("Test failed - capturing failure screenshot for: {}", testName);
        takeScreenshot("FAILED_" + testName);
    }
}