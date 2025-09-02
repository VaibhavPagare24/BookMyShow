package com.bookmyshow.utils;

import java.io.File;
import java.io.IOException;

public class AllureReportOpener {
	public static void cleanAllureResults() {
		File resultsDir = new File("target/allure-results");
		if (resultsDir.exists() && resultsDir.isDirectory()) {
			File[] files = resultsDir.listFiles();
			if (files != null) {
				for (File f : files) {
					try { f.delete(); } catch (Exception ignored) {}
				}
			}
		}
	}
	
	public static void openAllureReport() {
		try {
			ProcessBuilder generate = new ProcessBuilder("C:\\Users\\2421386\\Downloads\\allure-2.34.1\\allure-2.34.1\\bin\\allure.bat",
					"generate", "target/allure-results", "-o", "target/allure-report", "--clean");
			generate.inheritIO(); // Optional: shows output in console
			Process gen = generate.start();
			gen.waitFor();
			
			// Step 2: Open Allure report in browser
			ProcessBuilder open = new ProcessBuilder(
					"C:\\Users\\2421386\\Downloads\\allure-2.34.1\\allure-2.34.1\\bin\\allure.bat", "open",
					"target/allure-report");
			open.inheritIO();
			Process openProc = open.start();
			openProc.waitFor();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
