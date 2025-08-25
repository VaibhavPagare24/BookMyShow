package com.bookmyshow.pages;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.bookmyshow.models.EventDetail;
import com.bookmyshow.utils.JsonUtil;

public class EventsPage {

	WebDriver driver;
	WebDriverWait wait;
	
	@FindBy(linkText = "Events")
	private WebElement EventsTab;
	
//	@FindBy(xpath = "//*[@id=\"super-container\"]/div[2]/div[3]/div[1]/div[1]/div[2]/div[1]/div[2]/div[3]/div[2]/div/div")
//	WebElement weekendFilter;
	
	@FindBy(xpath = "//div[contains(text(),'This Weekend')]")
	WebElement weekendFilter;
	
//	@FindBy(xpath = "//*[@id=\"super-container\"]/div[2]/div[3]/div[1]/div[1]/div[2]/div[5]/div[1]/div[1]")
//	WebElement priceRange;
	
	@FindBy(xpath = "//div[contains(text(), 'Price')]")
	WebElement priceRange;
	
	
//	@FindBy(xpath = "//*[@id=\"super-container\"]/div[2]/div[3]/div[1]/div[1]/div[2]/div[5]/div[2]/div[3]/div[2]/div/div")
//	WebElement price;
	
	@FindBy(xpath = "//div[contains(normalize-space(.), '501 - 2000')]")
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
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", weekendFilter);
	}
	
	public void selectPriceRance() {
		// Open Price filter panel
		wait.until(ExpectedConditions.elementToBeClickable(priceRange));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", priceRange);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", price);
	}
	
	
	public void extractDetails() {
	    try {
	        // Wait until all event cards are visible
	        wait.until(ExpectedConditions.visibilityOfAllElements(eventCards));

	        TreeMap<Integer, String> priceMap = new TreeMap<>();
	        java.util.List<EventDetail> toWrite = new java.util.ArrayList<>();

	        for (WebElement card : eventCards) {
	            try {
	                String name = card.findElement(By.cssSelector(".elfplV")).getText().trim();
	                String priceText = card.findElement(By.xpath("(.//div[@class='sc-7o7nez-0 bsZIkT'])[2]")).getText().trim();
	                String priceClean = priceText.replace("₹", "").replace("onwards", "").replace(",", "").trim();
	                int priceValue;
	                if (priceClean.equalsIgnoreCase("free")) {
	                	priceValue = 0;
	                } else {
	                	priceValue = Integer.parseInt(priceClean);
	                }
	                priceMap.put(priceValue, name);
	                toWrite.add(new EventDetail(name, priceValue));
	            } catch (Exception innerEx) {
	                System.out.println("Error extracting from a card: " + innerEx.getMessage());
	            }
	        }

	        for (Map.Entry<Integer, String> entry : priceMap.entrySet()) {
	            System.out.println("Event: " + entry.getValue() + " | Price: ₹" + entry.getKey());
	        }
	        // Persist to JSON under target folder
	        String outFile = "target/events/events.json";
	        JsonUtil.write(outFile, toWrite);

	    } catch (Exception e) {
	        System.out.println("Error in extractDetails: " + e.getMessage());
	    }
	}
	

}
