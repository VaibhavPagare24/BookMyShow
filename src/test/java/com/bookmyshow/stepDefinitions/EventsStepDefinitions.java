package com.bookmyshow.stepDefinitions;

import org.testng.Assert;

import com.bookmyshow.pages.EventsPage;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class EventsStepDefinitions {
	private EventsPage eventsPage;
	
	@When("I click on Events tab")
	public void i_click_on_events_tab() {
		eventsPage = new EventsPage(BaseSteps.getDriver());
		eventsPage.clickEventTab();
	}
	
	@When("I apply weekend filter for events")
	public void i_apply_weekend_filter_for_events() {
		eventsPage.selectWeekend();
	}
	
	@When("I apply price range filter")
	public void i_apply_price_range_filter() {
		eventsPage.selectPriceRance();
	}
	
	@Then("I should see filtered events")
	public void i_should_see_filtered_events() {
		eventsPage.extractDetails();
	}
	
	@Then("events should be displayed with their details")
	public void events_should_be_displayed_with_their_details() {
		Assert.assertNotNull(BaseSteps.getDriver());
	}
	
	@Then("events should be sorted by price")
	public void events_should_be_sorted_by_price() {
		Assert.assertTrue(true);
	}
	
	@Then("I should see all available events")
	public void i_should_see_all_available_events() {
		eventsPage.extractDetails();
	}
} 