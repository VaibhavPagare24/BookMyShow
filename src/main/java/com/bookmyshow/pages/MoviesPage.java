package com.bookmyshow.pages;

import java.util.List;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MoviesPage {

	// WebDriver instance to interact with browser
	WebDriver driver;
	WebDriverWait wait;

	@FindBy(linkText = "Movies")
	private WebElement MoviesTab;

	@FindBy(xpath = "//div[@class='sc-7o7nez-0 hRJgHk']")
	private List<WebElement> languageElements;

	public MoviesPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	public void clickMoviesTab() {
		wait.until(ExpectedConditions.visibilityOf(MoviesTab)).click();
	}

	public void getLanguages() {
		wait.until(ExpectedConditions.visibilityOfAllElements(languageElements));

		for (WebElement language : languageElements) {
			System.out.println(language.getText());
		}
	}
}
