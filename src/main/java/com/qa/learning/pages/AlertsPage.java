package com.qa.learning.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// AlertsPage covers the demoqa.com Alerts page
// URL: https://demoqa.com/alerts
public class AlertsPage extends BasePage {

    private static final Logger log = LogManager.getLogger(AlertsPage.class);

    // Button that triggers a simple alert box
    private Locator alertButton;

    // Button that triggers a confirm box with OK/Cancel
    private Locator confirmButton;

    // Button that triggers a prompt box asking for input
    private Locator promptButton;

    // Result text shown after interacting with confirm box
    private Locator confirmResult;

    // Result text shown after interacting with prompt box
    private Locator promptResult;

    public AlertsPage(Page page) {
        super(page);
        log.info("Initializing AlertsPage locators");

        // Button IDs from demoqa.com
        alertButton = page.locator("#alertButton");
        confirmButton = page.locator("#confirmButton");
        promptButton = page.locator("#promtButton"); // note: typo in demoqa ID
        confirmResult = page.locator("#confirmResult");
        promptResult = page.locator("#promptResult");
    }

    // Navigate to the alerts page
    public void navigate() {
        log.info("Navigating to Alerts page");
        page.navigate("https://demoqa.com/alerts");
    }

    // Clicks the alert button and dismisses the alert
    // Must set up dialog listener BEFORE clicking
    public void clickAlertAndAccept() {
        log.info("Setting up alert listener and clicking alert button");

        // Register listener to auto-accept the alert when it appears
        page.onDialog(dialog -> {
            log.info("Alert appeared with message: {}", dialog.message());
            dialog.accept(); // clicks OK
        });

        alertButton.click();
        log.info("Alert accepted successfully");
    }

    // Clicks confirm box and accepts it (clicks OK)
    public void clickConfirmAndAccept() {
        log.info("Setting up confirm listener - will click OK");

        page.onDialog(dialog -> {
            log.info("Confirm box appeared: {}", dialog.message());
            dialog.accept(); // clicks OK
        });

        confirmButton.click();
    }

    // Clicks confirm box and dismisses it (clicks Cancel)
    public void clickConfirmAndDismiss() {
        log.info("Setting up confirm listener - will click Cancel");

        page.onDialog(dialog -> {
            log.info("Confirm box appeared: {}", dialog.message());
            dialog.dismiss(); // clicks Cancel
        });

        confirmButton.click();
    }

    // Clicks prompt box, types text and accepts
    public void clickPromptAndType(String text) {
        log.info("Setting up prompt listener - will type: {}", text);

        page.onDialog(dialog -> {
            log.info("Prompt appeared: {}", dialog.message());
            dialog.accept(text); // types text and clicks OK
        });

        promptButton.click();
    }

    // Returns the result text after confirm interaction
    public String getConfirmResult() {
        String result = confirmResult.innerText();
        log.info("Confirm result: {}", result);
        return result;
    }

    // Returns the result text after prompt interaction
    public String getPromptResult() {
        String result = promptResult.innerText();
        log.info("Prompt result: {}", result);
        return result;
    }
}