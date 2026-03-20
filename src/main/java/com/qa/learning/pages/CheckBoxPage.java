package com.qa.learning.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// CheckBoxPage covers the demoqa.com Checkbox page
// URL: https://demoqa.com/checkbox
public class CheckBoxPage extends BasePage {

    private static final Logger log = LogManager.getLogger(CheckBoxPage.class);

    // Locators
    private Locator homeCheckbox; // top level Home checkbox
    private Locator expandAllButton; // expands the full tree
    private Locator resultDisplay; // shows what is selected

    public CheckBoxPage(Page page) {
        super(page);
        log.info("Initializing CheckBoxPage locators");

        // From the HTML we can see the checkbox uses aria-label="Select Home"
        homeCheckbox = page.locator("span[aria-label='Select Home']");

        // Result display after selection
        resultDisplay = page.locator(".display-result");
    }

    public void navigate() {
        log.info("Navigating to CheckBox page");
        page.navigate("https://demoqa.com/checkbox");
    }

    // Expands the full checkbox tree so all options are visible
    // demoqa tree is already expanded on load - no action needed
    public void expandAll() {
        log.info("Tree already expanded on page load - skipping expand");
    }

    // Selects the Home checkbox which selects all children
    public void selectHomeCheckbox() {
        log.info("Selecting Home checkbox");
        // scroll into view first in case it's hidden behind ads
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