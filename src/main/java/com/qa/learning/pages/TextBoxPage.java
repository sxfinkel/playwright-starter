package com.qa.learning.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TextBoxPage extends BasePage {

    // Logger specific to this page object
    private static final Logger log = LogManager.getLogger(TextBoxPage.class);

    private Locator fullNameInput;
    private Locator emailInput;
    private Locator currentAddressInput;
    private Locator permanentAddressInput;
    private Locator submitButton;
    private Locator outputName;
    private Locator outputEmail;

    public TextBoxPage(Page page) {
        super(page);
        log.info("Initializing TextBoxPage locators");
        fullNameInput = page.locator("#userName");
        emailInput = page.locator("#userEmail");
        currentAddressInput = page.locator("#currentAddress");
        permanentAddressInput = page.locator("#permanentAddress");
        submitButton = page.locator("#submit");
        outputName = page.locator("#output #name");
        outputEmail = page.locator("#output #email");
    }

    public void navigate() {
        log.info("Navigating to Text Box page");
        page.navigate("https://demoqa.com/text-box");
    }

    public void fillForm(String name, String email, String current, String permanent) {
        log.info("Filling form with name: {} and email: {}", name, email);
        fullNameInput.fill(name);
        emailInput.fill(email);
        currentAddressInput.fill(current);
        permanentAddressInput.fill(permanent);
        log.info("Clicking submit button");
        submitButton.click();
    }

    public String getOutputName() {
        String name = outputName.innerText();
        log.info("Output name displayed: {}", name);
        return name;
    }

    public String getOutputEmail() {
        String email = outputEmail.innerText();
        log.info("Output email displayed: {}", email);
        return email;
    }
}