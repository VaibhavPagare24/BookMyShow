package com.bookmyshow.pages;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SportsPage {

	// WebDriver instance to interact with browser
	WebDriver driver;

	public SportsPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void clickSportsTab() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement sports = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Sports")));
//		driver.findElement(By.linkText("Sports"));
		sports.click();

		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
				"//*[@id=\"super-container\"]/div[2]/div[3]/div[1]/div[1]/div[2]/div[1]/div[2]/div[3]/div[2]/div/div")))
				.click();
	}
	
	public void extractDetails() {
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a.ctsexn.uPavs")));

		// Then capture the list
		List<WebElement> eventCards = driver.findElements(By.cssSelector("a.ctsexn.uPavs"));
		System.out.println("Found event cards: " + eventCards.size());

		System.out.println(eventCards.size());
		TreeMap<Integer, String> priceMap = new TreeMap<>();

		for (WebElement card : eventCards) {
			String name = card.findElement(By.cssSelector(".elfplV")).getText().trim();
			String price = card.findElement(By.xpath("(.//div[@class='sc-7o7nez-0 bsZIkT'])[2]")).getText().trim();
			String priceClean = price.replace("₹", "").replace("onwards", "").trim();
			//System.out.println("Event: " + name + " | Price: " + price);
			priceMap.put(Integer.parseInt(priceClean), name);
		}

		for(Map.Entry<Integer, String> tm : priceMap.entrySet()) {
			System.out.println("Event: " + tm.getValue() + " Price: Rs." + tm.getKey());
		}
		
	}
}
