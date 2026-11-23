package com.erzhena.tests;

import com.erzhena.pages.InventoryPage;
import com.erzhena.pages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class LoginTest {

    private WebDriver driver;
    private LoginPage loginPage;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        loginPage = new LoginPage(driver);
    }

    @Test
    public void loginWithLockedOutUserShowsError() {
        loginPage.open();
        loginPage.loginExpectingFailure("locked_out_user", "secret_sauce");

        assertEquals(
                loginPage.getErrorMessage(),
                "Epic sadface: Sorry, this user has been locked out."
        );
    }

    @Test
    public void standardUserCanLogIn() {
        loginPage.open();
        InventoryPage inventoryPage = loginPage.loginAs("standard_user", "secret_sauce");

        assertEquals(inventoryPage.getTitle(), "Products");
        assertEquals(inventoryPage.getItemsCount(), 6);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
