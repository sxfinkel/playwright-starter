package com.qa.learning.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;

// LoginPage represents the login screen
// It knows where every element is - tests never need to know locator details
public class LoginPage extends BasePage {

    // Locators define HOW to find each element on the page
    // Keeping them here means if the UI changes, you fix it in one place only
    private Locator usernameField;
    private Locator passwordField;
    private Locator loginButton;
    private Locator errorMessage;

    // Constructor - find and store all elements when the page object is created
    public LoginPage(Page page) {
        super(page); // calls BasePage constructor to set up the page object
        usernameField = page.locator("#username"); // finds by ID
        passwordField = page.locator("#password"); // finds by ID
        loginButton = page.locator("button[type='submit']"); // finds by attribute
        errorMessage = page.locator("#flash"); // error banner element
    }

    // Navigate directly to the login page URL
    public void navigate() {
        page.navigate("https://the-internet.herokuapp.com/login");
    }

    // Reusable login method - accepts any username/password combination
    // Tests call this instead of filling fields themselves
    public void login(String username, String password) {
        usernameField.fill(username); // types into username field
        passwordField.fill(password); // types into password field
        loginButton.click(); // clicks the submit button
    }

    // Returns the error message text so tests can assert against it
    public String getErrorMessage() {
        return errorMessage.innerText();
    }
}