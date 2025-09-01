package com.bookmyshow.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {
	private int retryCount = 0;
	private final int maxRetryCount;
	
	public RetryAnalyzer() {
		this(1);
	}
	
	public RetryAnalyzer(int maxRetryCount) {
		this.maxRetryCount = Math.max(0, maxRetryCount);
	}
	
	@Override
	public boolean retry(ITestResult result) {
		if (retryCount < maxRetryCount) {
			retryCount++;
			return true;
		}
		return false;
	}
}