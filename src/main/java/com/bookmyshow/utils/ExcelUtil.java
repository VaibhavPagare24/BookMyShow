package com.bookmyshow.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for Excel operations using Apache POI
 */
public class ExcelUtil {

	private static final Logger logger = LogManager.getLogger(ExcelUtil.class);

	/**
	 * Read test data from Excel file
	 * 
	 * @param filePath  Path to Excel file
	 * @param sheetName Sheet name
	 * @return List of test data maps
	 */

	private Workbook workbook;
	private Sheet sheet;
	private FileInputStream fileInputStream;
	private FileOutputStream fileOutputStream;

	public ExcelUtil(String filePath, String sheetName) {
		try {
			fileInputStream = new FileInputStream(filePath);
			workbook = new XSSFWorkbook(fileInputStream);
			sheet = workbook.getSheet(sheetName);
			if (sheet == null) {
				throw new IllegalArgumentException("Sheet '" + sheetName + "' not found in Excel file.");
			}
		} catch (IOException e) {
			throw new RuntimeException("Failed to load Excel file: " + filePath, e);
		}
	}

	/**
	 * Returns data of a column as a list, skipping the header.
	 */
	public List<String> getColumnData(int columnIndex) {
		
		
		List<String> data = new ArrayList<>();
		int rowCount = sheet.getPhysicalNumberOfRows();
		for (int i = 1; i < rowCount; i++) { // skip header
			Row row = sheet.getRow(i);
			if (row != null) {
				Cell cell = row.getCell(columnIndex);
				if (cell != null) {
					data.add(getCellValueAsString(cell));
				}
			}
		}
		
		//fModel = new FlipkartModel(data.get(0));
		return data;
	}

	private String getCellValueAsString(Cell cell) {
		DataFormatter formatter = new DataFormatter();
		return formatter.formatCellValue(cell);
	}

	public void close() {
		try {
			if (fileInputStream != null)
				fileInputStream.close();
			if (workbook != null)
				workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeTestResult(String filePath, int rowNum, String firstProductPrice, String secondProductPrice,
			String totalCartAmount, String status) {
		try {
			XSSFSheet resultSheet = (XSSFSheet) workbook.getSheet("TestResults");
			if (resultSheet == null) {
				resultSheet = (XSSFSheet) workbook.createSheet("TestResults");
			}

			// Check if header row exists; if not, create it
			if (resultSheet.getLastRowNum() == 0 && resultSheet.getRow(0) == null) {
				XSSFRow headerRow = resultSheet.createRow(0);
				headerRow.createCell(0).setCellValue("Test Case");
				headerRow.createCell(1).setCellValue("First Product Price");
				headerRow.createCell(2).setCellValue("Second Product Price");
				headerRow.createCell(3).setCellValue("Total Cart Amount");
				headerRow.createCell(4).setCellValue("Status");
			}

			// Get the next available row
			int rowIndex = resultSheet.getLastRowNum() + 1;

			// Write data
			XSSFRow resultRow = resultSheet.createRow(rowIndex);
			resultRow.createCell(0).setCellValue("Add Products to Cart Test");
			resultRow.createCell(1).setCellValue(firstProductPrice);
			resultRow.createCell(2).setCellValue(secondProductPrice);
			resultRow.createCell(3).setCellValue(totalCartAmount);
			resultRow.createCell(4).setCellValue(status);

			// Save the result to the file
			fileOutputStream = new FileOutputStream(filePath);
			workbook.write(fileOutputStream);
			fileOutputStream.close();
		} catch (IOException e) {
			logger.error("Failed to write to Excel file: {}", e.getMessage());
		}
	}

}
