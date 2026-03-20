package com.qa.learning.pages;

import com.microsoft.playwright.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// BasePage is the parent class for all page objects
// All page objects inherit the logger from here
public class BasePage {

    // Logger instance — every class that extends BasePage can use this
    protected static final Logger log = LogManager.getLogger(BasePage.class);

    // 'page' represents the browser page
    protected Page page;

    public BasePage(Page page) {
        this.page = page;
    }

    // Log and return the current page title
    public String getTitle() {
        String title = page.title();
        log.info("Current page title: {}", title);
        return title;
    }

    // Log navigation actions
    public void navigateTo(String url) {
        log.info("Navigating to: {}", url);
        page.navigate(url);
    }
}