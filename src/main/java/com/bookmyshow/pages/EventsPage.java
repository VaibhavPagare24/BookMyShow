package com.bookmyshow.pages;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EventsPage {

	WebDriver driver;
	WebDriverWait wait;
	
	@FindBy(linkText = "Events")
	private WebElement EventsTab;
	
	@FindBy(xpath = "//*[@id=\"super-container\"]/div[2]/div[3]/div[1]/div[1]/div[2]/div[1]/div[2]/div[3]/div[2]/div/div")
	WebElement weekendFilter;
	
	@FindBy(xpath = "//*[@id=\"super-container\"]/div[2]/div[3]/div[1]/div[1]/div[2]/div[5]/div[1]/div[1]")
	WebElement priceRange;
	
	@FindBy(xpath = "//*[@id=\"super-container\"]/div[2]/div[3]/div[1]/div[1]/div[2]/div[5]/div[2]/div[3]/div[2]/div/div")
	WebElement price;
	
	@FindBy(xpath = "//a[contains(@class, 'sc-133848s-11')]")
	private List<WebElement> eventCards;


	public EventsPage(WebDriver driver) {
		this.driver = driver; // Store driver reference for page interactions
		PageFactory.initElements(driver, this); // Initialize all @FindBy elements
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	public void clickEventTab() {
		wait.until(ExpectedConditions.visibilityOf(EventsTab)).click();
	}

	public void selectWeekend() {
		wait.until(ExpectedConditions.elementToBeClickable(weekendFilter)).click();
	}
	
	public void selectPriceRance() {
		wait.until(ExpectedConditions.elementToBeClickable(priceRange)).click();
		wait.until(ExpectedConditions.elementToBeClickable(price)).click();
	}
	
	
	public void extractDetails() {
	    try {
	        // Wait until all event cards are visible
	        wait.until(ExpectedConditions.visibilityOfAllElements(eventCards));
	        //System.out.println("Found event cards: " + eventCards.size());

	        TreeMap<Integer, String> priceMap = new TreeMap<>();

	        for (WebElement card : eventCards) {
	            try {
	                // Extract event name
	                String name = card.findElement(By.cssSelector(".elfplV")).getText().trim();
	                String price = card.findElement(By.xpath("(.//div[@class='sc-7o7nez-0 bsZIkT'])[2]")).getText().trim();
	    			String priceClean = price.replace("₹", "").replace("onwards", "").trim();
	    			priceMap.put(Integer.parseInt(priceClean), name);

	            } catch (Exception innerEx) {
	                System.out.println("Error extracting from a card: " + innerEx.getMessage());
	            }
	        }

	        // Print collected results
	        for (Map.Entry<Integer, String> entry : priceMap.entrySet()) {
	            System.out.println("Event: " + entry.getValue() + " | Price: ₹" + entry.getKey());
	        }

	    } catch (Exception e) {
	        System.out.println("Error in extractDetails: " + e.getMessage());
	    }
	}

}
