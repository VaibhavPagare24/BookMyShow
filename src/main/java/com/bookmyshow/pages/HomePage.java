package com.bookmyshow.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
	
	// WebDriver instance to interact with browser
		WebDriver driver;

	public HomePage(WebDriver driver) {
		this.driver = driver; // Store driver reference for page interactions
		PageFactory.initElements(driver, this); // Initialize all @FindBy elements
	}
	
	
	public void selectCity() {
		try {
			// Wait for popup close button to be visible
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			WebElement location = wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"bottomSheet-model-close\"]/div")));
			
			boolean displayed = location.isDisplayed();

			if (displayed) {
				System.out.println("Displayed");
			} else {
				System.out.println("Not displayed");
			}

			WebElement search = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"dummy\"]")));
			// driver.findElement(By.xpath("//*[@id=\"dummy\"]"));
			search.sendKeys("Coimbatore");
			search.sendKeys(Keys.ENTER);
		} catch (Exception e) {
			// If popup doesn't appear, just continue (no action needed)
			System.out.println("Login popup not found.");
		}
	}
}
