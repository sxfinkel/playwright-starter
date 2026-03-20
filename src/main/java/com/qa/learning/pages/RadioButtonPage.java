package com.qa.learning.pages;

// Import Playwright Page and Locator classes
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;

/**
 * Page Object Model (POM) class for Radio Button interactions.
 * Encapsulates locators and actions for selecting radio buttons
 * and validating the result message.
 */
public class RadioButtonPage extends BasePage {

    // Locator for "Yes" radio button label
    // Using the label is more reliable because the actual input may be hidden
    private final Locator yesRadio;

    // Locator for "Impressive" radio button label
    private final Locator impressiveRadio;

    // Locator for "No" radio button input
    // We use the input here so we can validate whether it is disabled
    private final Locator noRadio;

    // Locator for result message displayed after selecting a radio button
    private final Locator result;

    /**
     * Constructor initializes all locators for the page.
     *
     * @param page Playwright Page instance passed from test class
     */
    public RadioButtonPage(Page page) {
        super(page);

        log.info("Initializing RadioButtonPage locators");

        // Use label locators for clickable radio button options
        yesRadio = page.locator("label[for='yesRadio']");
        impressiveRadio = page.locator("label[for='impressiveRadio']");

        // Use input locator for disabled-state validation
        noRadio = page.locator("#noRadio");

        // Result text displayed after a successful selection
        result = page.locator(".text-success");
    }

    /**
     * Navigates to the Radio Button page.
     */
    public void navigate() {
        log.info("Navigating to Radio Button Page");

        // Use full URL unless a base URL has been configured elsewhere
        page.navigate("https://demoqa.com/radio-button");
    }

    /**
     * Selects the "Yes" radio button.
     */
    public void selectYes() {
        log.info("Selecting 'Yes' radio button");
        yesRadio.click();
    }

    /**
     * Selects the "Impressive" radio button.
     */
    public void selectImpressive() {
        log.info("Selecting 'Impressive' radio button");
        impressiveRadio.click();
    }

    /**
     * Returns whether the "No" radio button is disabled.
     *
     * @return true if disabled, otherwise false
     */
    public boolean isNoRadioDisabled() {
        boolean disabled = noRadio.isDisabled();
        log.info("'No' radio button disabled status: {}", disabled);
        return disabled;
    }

    /**
     * Retrieves the result message displayed after selection.
     *
     * @return result text as String
     */
    public String getResultText() {
        String resultText = result.textContent();
        log.info("Result text: {}", resultText);
        return resultText != null ? resultText.trim() : "";
    }
}