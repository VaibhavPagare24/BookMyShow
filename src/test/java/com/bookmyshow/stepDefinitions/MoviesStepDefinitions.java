package com.bookmyshow.stepDefinitions;

import com.bookmyshow.pages.MoviesPage;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class MoviesStepDefinitions {
	private MoviesPage moviesPage;
	
	@When("I click on Movies tab")
	public void i_click_on_movies_tab() {
		moviesPage = new MoviesPage(BaseSteps.getDriver());
		moviesPage.clickMoviesTab();
	}
	
	@Then("I should see all available movie languages")
	public void i_should_see_all_available_movie_languages() {
		moviesPage.getLanguages();
	}
} 