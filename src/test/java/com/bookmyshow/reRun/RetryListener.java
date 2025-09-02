package com.bookmyshow.reRun;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

public class RetryListener implements IAnnotationTransformer {
	
	@Override
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
		Class<? extends org.testng.IRetryAnalyzer> retryAnalyzer = annotation.getRetryAnalyzerClass();
		if (retryAnalyzer == null) {
			annotation.setRetryAnalyzer(RetryAnalyzer.class);
		}
	}
} 