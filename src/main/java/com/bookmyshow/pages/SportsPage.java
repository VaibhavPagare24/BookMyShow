package com.bookmyshow.pages;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SportsPage {

	private WebDriver driver;
	private WebDriverWait wait;

	// --- Locators using @FindBy ---

	@FindBy(linkText = "Sports")
	private WebElement sportsTab;

	@FindBy(xpath = "//*[@id='super-container']/div[2]/div[3]/div[1]/div[1]/div[2]/div[1]/div[2]/div[3]/div[2]/div/div")
	private WebElement weekendFilter;

	@FindBy(css = "a.ctsexn.uPavs")
	private List<WebElement> eventCards;

	@FindBy(css = ".elfplV")
	private WebElement eventName;

	@FindBy(xpath = "(.//div[@class='sc-7o7nez-0 bsZIkT'])[2]")
	private WebElement eventPrice;

	// --- Constructor ---
	public SportsPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		PageFactory.initElements(driver, this);
	}

	// --- Click Sports Tab and apply weekend filter ---
	public void clickSportsTab() {
		wait.until(ExpectedConditions.visibilityOf(sportsTab)).click();
		wait.until(ExpectedConditions.elementToBeClickable(weekendFilter)).click();
	}

	// --- Extract event details ---
	public void extractDetails() {
		wait.until(ExpectedConditions.visibilityOfAllElements(eventCards));

		//System.out.println("Found event cards: " + eventCards.size());
		TreeMap<Integer, String> priceMap = new TreeMap<>();

		for (WebElement card : eventCards) {
			String name = card.findElement(By.cssSelector(".elfplV")).getText().trim();
			String price = card.findElement(By.xpath("(.//div[@class='sc-7o7nez-0 bsZIkT'])[2]")).getText().trim();
			String priceClean = price.replace("₹", "").replace("onwards", "").trim();
			priceMap.put(Integer.parseInt(priceClean), name);
		}

		for (Map.Entry<Integer, String> tm : priceMap.entrySet()) {
			System.out.println("Sports Event: " + tm.getValue() + " | Price: Rs." + tm.getKey());
		}
	}
}
