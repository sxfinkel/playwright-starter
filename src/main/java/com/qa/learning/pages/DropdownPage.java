package com.qa.learning.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DropdownPage extends BasePage {
    private static final Logger log = LogManager.getLogger(DropdownPage.class);

    private Locator oldSelectMenu; // Locator for the old style select menu
    private Locator reactSelectInput; // Locator for the React Select input

    public DropdownPage(Page page) {
        super(page); // pass to BasePage instead of this.page = page
        log.info("Initializing DropdownPage locators");
        oldSelectMenu = page.locator("#oldSelectMenu");
        reactSelectInput = page.locator("#react-select-2-input");

    }

    /**
     * Navigates to the Select Manu page.
     */
    public void navigate() {
        log.info("Navigating to Dropdown Page");

        // Use full URL unless a base URL has been configured elsewhere
        page.navigate("https://demoqa.com/select-menu");
    }

    /**
     * Selects the "blue" Select Manu page.
     */
    public void selectOldMenuOption(String value) {
        log.info("Selecting option: {}", value);
        page.selectOption("#oldSelectMenu", value); // Playwright built-in for native selects
    }

    /**
     * select an option from the react select "selct value" dropdown
     * by its option id suffix (e.g "0-0" for gorup 1 option 1, "1-2" for group 2
     * option 3)
     */
    public void selectReactSelectValue(String optionIdSuffix) {
        log.info("Opening React Select Dowpdown");
        reactSelectInput.click(); // open the dropdown

        log.info("Selecting option: {}", optionIdSuffix);
        page.locator("#react-select-2-option-" + optionIdSuffix).click(); // click the option by its id
    }

    /*
     * returns the currently displayed value in the React Select Dropdown
     * 
     * @return the selected value
     */
    public String getReactDropdownSelectedText() {
        String text = page.locator("#react-select-2-container .css-single-value, [class$='singleValue']").innerText();
        log.info("React dropdown selected text: {}", text);
        return text;
    }

    // Get the currently selected value
    public String getOldMenuSelectedValue() {
        String value = oldSelectMenu.inputValue();
        log.info("Selected value: {}", value);
        return value;
    }

}
