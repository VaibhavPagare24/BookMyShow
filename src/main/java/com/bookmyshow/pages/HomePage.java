
package com.bookmyshow.pages;

import java.time.Duration;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

	WebDriver driver;

	// Constructor
	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this); // Initialize @FindBy elements
	}

	// Element for popup close button (location selection modal)
	@FindBy(xpath = "//*[@id=\"bottomSheet-model-close\"]/div")
	private WebElement locationPopupClose;

	// Element for the city search input
	@FindBy(xpath = "//*[@id=\"dummy\"]")
	private WebElement citySearchBox;

	// Method to select city
		public void selectCity(String city) {
			try {
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	
				// Wait for popup and check if it is displayed
				wait.until(ExpectedConditions.visibilityOf(locationPopupClose));
				// Wait for the search input and perform city selection
				wait.until(ExpectedConditions.visibilityOf(citySearchBox));
				citySearchBox.sendKeys(city);
				citySearchBox.sendKeys(Keys.ENTER);
			} catch (Exception e) {
				System.out.println("Login popup not found.");
			}
		}
}
