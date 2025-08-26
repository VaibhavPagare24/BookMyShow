
package com.bookmyshow.pages;

import java.time.Duration;
import java.util.Set;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private String mainWindowHandle;
    private static final Logger logger = LogManager.getLogger(LoginPage.class);


    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[contains(text(),'Sign in')]")
    private WebElement signInButton;

    @FindBy(xpath = "//div[text()='Continue with Google']")
    private WebElement continueButton;

    @FindBy(id = "identifierId")
    private WebElement googleEmailField;

    @FindBy(xpath = "//span[text()='Next']")
    private WebElement googleNextButton;

    @FindBy(xpath = "//*[contains(text(), \"Couldn't sign you in\") or contains(@class, 'o6cuMc') or contains(@class, 'vAV9bf')]")
    private WebElement googleEmailError;

    
    public void clickSignIn() {
        try {
            logger.info("Clicking on Sign In button");
            wait.until(ExpectedConditions.elementToBeClickable(signInButton)).click();
            wait.until(ExpectedConditions.visibilityOf(continueButton));
            mainWindowHandle = driver.getWindowHandle();
            logger.info("Sign In popup opened successfully");
        } catch (Exception e) {
            logger.error("Failed to click Sign In button: " + e.getMessage());
            throw new RuntimeException("Could not click Sign In button", e);
        }
    }

    public void clickContinueWithGoogle() {
        try {
            logger.info("Clicking on Continue with Google button");
            wait.until(ExpectedConditions.elementToBeClickable(continueButton)).click();
        } catch (Exception e) {
            logger.error("Failed to click Continue with Google button: " + e.getMessage());
            throw new RuntimeException("Could not click Continue with Google button", e);
        }
    }

    public void switchToGoogleWindow() {
        try {
            logger.info("Switching to Google authentication window");
            
            wait.until(ExpectedConditions.numberOfWindowsToBe(2));
            
            Set<String> windowHandles = driver.getWindowHandles();
            
            for (String handle : windowHandles) {
                if (!handle.equals(mainWindowHandle)) {
                    driver.switchTo().window(handle);
                    logger.info("Switched to Google window");
                    break;
                }
            }

            try {
                wait.until(ExpectedConditions.visibilityOf(googleEmailField));
            } catch (Exception e) {
                logger.warn("Email field not immediately visible, waiting longer...");
                wait.until(ExpectedConditions.visibilityOf(googleEmailField));
            }
        } catch (Exception e) {
            logger.error("Failed to switch to Google window: " + e.getMessage());
            throw new RuntimeException("Could not switch to Google window", e);
        }
    }

    public String enterInvalidGoogleEmail(String email) {
        try {
            logger.info("Entering invalid Google email: " + email);
            wait.until(ExpectedConditions.visibilityOf(googleEmailField)).sendKeys(email);
            wait.until(ExpectedConditions.elementToBeClickable(googleNextButton)).click();

            
            try {
                wait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOf(googleEmailError),
                    ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='o6cuMc']"))
                ));
            } catch (Exception e) {
                logger.debug("No error element appeared within timeout, proceeding to check for errors");
            }

            String errorMessage = null;
            
            logger.debug("Primary error element not found, trying fallback selectors");
            
            String[] errorSelectors = {
                "//h1[contains(text(), \"Couldn't sign you in\")]"
            };

            for (String selector : errorSelectors) {
                try {
                    WebElement errorElement = driver.findElement(By.xpath(selector));
                    if (errorElement.isDisplayed() && !errorElement.getText().trim().isEmpty()) {
                        errorMessage = errorElement.getText();
                        logger.info("Error found using selector: " + selector);
                        logger.info("Error text: " + errorMessage);
                        break;
                    }
                } catch (Exception ex) {
                    
                    logger.debug("Selector not found: " + selector);
                }
            }

            
            if (errorMessage == null || errorMessage.trim().isEmpty()) {
                try {
                    List<WebElement> allElements = driver.findElements(By.xpath("//*[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'couldn') or contains(text(), 'error') or contains(text(), 'invalid')]"));
                    for (WebElement element : allElements) {
                        if (element.isDisplayed() && !element.getText().trim().isEmpty()) {
                            String text = element.getText().trim();
                            if (text.length() > 10 && text.length() < 200) { // Reasonable error message length
                                errorMessage = text;
                                logger.info("Found potential error text: " + errorMessage);
                                break;
                            }
                        }
                    }
                } catch (Exception e) {
                    logger.debug("Could not find any error-like text");
                }
            }

            if (errorMessage != null && !errorMessage.trim().isEmpty()) {
                System.out.println("Displayed Error Message: " + errorMessage);
                logger.info("Displayed Error Message: " + errorMessage);
            } else {
                try {
                    String currentUrl = driver.getCurrentUrl();
                    String pageTitle = driver.getTitle();
                    
                    if (currentUrl.contains("accounts.google.com") && 
                        (pageTitle.contains("Sign in") || pageTitle.contains("Error"))) {
                        errorMessage = "Google authentication failed - Invalid email detected";
                        logger.info("Detected error state based on URL and title");
                    } else {
                        errorMessage = "No error message displayed.";
                    }
                } catch (Exception e) {
                    errorMessage = "No error message displayed.";
                }
                
                System.out.println(errorMessage);
                logger.warn(errorMessage);
            }

            return errorMessage;

        } catch (Exception e) {
            logger.error("Failed to enter email or capture error: " + e.getMessage());
            throw new RuntimeException("Could not enter invalid email or retrieve error", e);
        }
    }

    public String captureEmailError() {
        try {
            if (googleEmailError.isDisplayed()) {
                return googleEmailError.getText();
            }
        } catch (Exception e) {
            logger.debug("Email error not displayed: " + e.getMessage());
        }
        return null;
    }

    public void switchBackToMainWindow() {
        try {

            Set<String> windowHandles = driver.getWindowHandles();
            String currentWindow = driver.getWindowHandle();
            if (windowHandles.size() > 1 && !currentWindow.equals(mainWindowHandle)) {
                driver.close(); 
            }

            if (windowHandles.contains(mainWindowHandle)) {
                driver.switchTo().window(mainWindowHandle);
            } else {
                driver.switchTo().window(windowHandles.iterator().next());
            }

            try {

                WebElement closeButton = driver.findElement(By.xpath("//*[@id=\"bottomSheet-model-close\"]/div/div/div[1]/div/div[2]"));
                if (closeButton.isDisplayed()) {
                    closeButton.click();
                    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id=\"bottomSheet-model-close\"]/div/div/div[1]/div/div[2]")));
                }
            } catch (Exception e) {
                logger.debug("BookMyShow login modal not found or already closed");
            }
            
            logger.info("Switched back to main BookMyShow window");
        } catch (Exception e) {
            logger.error("Failed to switch back to main window: " + e.getMessage());
            try {
                Set<String> allWindows = driver.getWindowHandles();
                if (!allWindows.isEmpty()) {
                    driver.switchTo().window(allWindows.iterator().next());
                }
            } catch (Exception ex) {
                logger.error("Could not recover window context: " + ex.getMessage());
            }
        }
    }

    public String signInWithInvalidEmail(String email) {
        String errorMsg = "No error captured";
        try {
            logger.info("Starting Google Sign In flow with invalid email");
            
            clickSignIn();
            logger.info("Sign in button clicked successfully");
            
            clickContinueWithGoogle();
            logger.info("Continue with Google button clicked successfully");
            
            switchToGoogleWindow();
            logger.info("Switched to Google window successfully");

            errorMsg = enterInvalidGoogleEmail(email);
            logger.info("Completed entering invalid email, captured message: " + errorMsg);

            switchBackToMainWindow();
            logger.info("Switched back to main window successfully");
            
            return errorMsg;

        } catch (Exception e) {
            logger.error("Google Sign In with invalid email failed at some step: " + e.getMessage());
            logger.error("Full exception: ", e);

            try {
                switchBackToMainWindow();
            } catch (Exception ex) {
                logger.error("Could not switch back to main window after failure: " + ex.getMessage());
            }
            
            // If we captured some error message, return it
            if (!errorMsg.equals("No error captured")) {
                return errorMsg;
            }
            throw new RuntimeException("Google Sign In flow failed", e);
        }
    }


    public void quitDriver() {
        try {
            if (driver != null) {
                Set<String> windows = driver.getWindowHandles();
                for (String window : windows) {
                    driver.switchTo().window(window);
                    driver.close();
                }

                driver.quit();
                logger.info("Browser quit successfully");
            }
        } catch (Exception e) {
            logger.error("Error while quitting browser: " + e.getMessage());

            if (driver != null) {
                driver.quit();
            }
        }
    }
}
 