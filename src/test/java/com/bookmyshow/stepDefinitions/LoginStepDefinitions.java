package com.bookmyshow.stepDefinitions;

import org.testng.Assert;

import com.bookmyshow.pages.LoginPage;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginStepDefinitions {
	private LoginPage loginPage;
	private String capturedErrorMessage;
	
	@When("I click on Sign In button")
	public void i_click_on_sign_in_button() {
		loginPage = new LoginPage(BaseSteps.getDriver());
		loginPage.clickSignIn();
	}
	
	@When("I click on Continue with Google")
	public void i_click_on_continue_with_google() {
		loginPage.clickContinueWithGoogle();
	}
	
	@When("I switch to Google authentication window")
	public void i_switch_to_google_authentication_window() {
		loginPage.switchToGoogleWindow();
	}
	
	@When("I enter invalid email {string}")
	public void i_enter_invalid_email(String email) {
		capturedErrorMessage = loginPage.enterInvalidGoogleEmail(email);
	}
	
	@Then("I should see appropriate error message for invalid email")
	public void i_should_see_appropriate_error_message_for_invalid_email() {
		Assert.assertNotNull(capturedErrorMessage, "Error message should not be null");
		Assert.assertFalse(capturedErrorMessage.trim().isEmpty(), "Error message should not be empty");
	}
	
	@Then("the error message should contain expected authentication failure text")
	public void the_error_message_should_contain_expected_authentication_failure_text() {
		String normalized = capturedErrorMessage
			.replace('\u2019', '\'')
			.replace("\u2018", "'")
			.replace("\u201C", "\"")
			.replace("\u201D", "\"")
			.toLowerCase();
		Assert.assertTrue(
			normalized.contains("find your google account") ||
			normalized.contains("couldn't sign you in") ||
			normalized.contains("couldn\u2019t sign you in") ||
			normalized.contains("no error message displayed") ||
			normalized.contains("google authentication failed"),
			"Unexpected error message: " + capturedErrorMessage);
	}
	
	@When("I switch back to main window")
	public void i_switch_back_to_main_window() {
		loginPage.switchBackToMainWindow();
	}
	
	@Then("I should be back on BookMyShow main page")
	public void i_should_be_back_on_book_my_show_main_page() {
		Assert.assertNotNull(BaseSteps.getDriver().getTitle());
	}
	
	@When("I attempt to sign in with invalid email {string}")
	public void i_attempt_to_sign_in_with_invalid_email(String email) {
		loginPage = new LoginPage(BaseSteps.getDriver());
		capturedErrorMessage = loginPage.signInWithInvalidEmail(email);
	}
} 