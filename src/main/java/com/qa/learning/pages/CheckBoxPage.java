package com.qa.learning.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// CheckBoxPage represents the demoqa.com checkbox page
// Extends BasePage to inherit logging and screenshot capabilities
public class CheckBoxPage extends BasePage {

    private static final Logger log = LogManager.getLogger(CheckBoxPage.class);

    // Locators for elements on the page
    private Locator homeCheckbox;
    private Locator resultDisplay;

    // Constructor - accepts the Playwright Page object
    public CheckBoxPage(Page page) {
        super(page); // calls BasePage constructor
        log.info("Initializing CheckBoxPage locators");

        // Using aria-label to find the Home checkbox
        homeCheckbox = page.locator("span[aria-label='Select Home']");

        // Result text displayed after a checkbox is selected
        resultDisplay = page.locator(".display-result");
    }

    // Navigate to the checkbox page
    public void navigate() {
        log.info("Navigating to CheckBox page");
        page.navigate("https://demoqa.com/checkbox");
    }

    // Scrolls to and clicks the Home checkbox
    public void selectHomeCheckbox() {
        log.info("Selecting Home checkbox");
        homeCheckbox.scrollIntoViewIfNeeded();
        homeCheckbox.click();
    }

    // Returns the result text shown after selection
    public String getSelectedResult() {
        String result = resultDisplay.innerText();
        log.info("Selected result displayed: {}", result);
        return result;
    }

    // Returns true if the result section is visible
    public boolean isResultDisplayed() {
        boolean visible = resultDisplay.isVisible();
        log.info("Result display visible: {}", visible);
        return visible;
    }
}