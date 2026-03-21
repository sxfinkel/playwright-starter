package com.qa.learning.pages;

import com.microsoft.playwright.Page;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.ByteArrayInputStream;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// BasePage is the parent class for all page objects
// All page objects inherit screenshot and Allure attachment capability
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

    // Navigates to a URL
    public void navigateTo(String url) {
        log.info("Navigating to: {}", url);
        page.navigate(url);
    }

    // Takes screenshot, saves to file AND attaches to Allure report
    public void takeScreenshot(String fileName) {
        takeScreenshot(fileName, "");
    }

    // Takes screenshot with step name, saves to file AND attaches to Allure report
    public void takeScreenshot(String testName, String step) {
        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

        // Build meaningful name
        String name = step.isEmpty() ? testName : testName + "_" + step;
        String filePath = "target/screenshots/" + name + "_" + timestamp + ".png";

        log.info("Capturing screenshot: {}", filePath);

        // Take screenshot as byte array — needed for Allure attachment
        byte[] screenshot = page.screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get(filePath))
                .setFullPage(true));

        // Attach screenshot directly to Allure report
        Allure.addAttachment(name, new ByteArrayInputStream(screenshot));

        log.info("Screenshot saved and attached to Allure report: {}", filePath);
    }

    // Failure screenshot — always captured, clearly labeled
    public void takeScreenshotOnFailure(String testName) {
        log.warn("Test failed - capturing failure screenshot: {}", testName);
        takeScreenshot("FAILED_" + testName);
    }
}