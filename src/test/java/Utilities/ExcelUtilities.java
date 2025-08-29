package Utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class ExcelUtilities {

    private Workbook workbook;
    private Sheet sheet;
    private String filePath;

    // Constructor to load Excel file and sheet
    public ExcelUtilities(String filePath, String sheetName) {
        try {
            FileInputStream fis = new FileInputStream(filePath);
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheet(sheetName);
            this.filePath = filePath;
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Get cell value as string
    public String getCellData(int rowNum, int colNum) {
        try {
            Row row = sheet.getRow(rowNum);
            if (row == null) return "";
            Cell cell = row.getCell(colNum);
            if (cell == null) return "";

            DataFormatter formatter = new DataFormatter();
            return formatter.formatCellValue(cell);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    // Set value in a cell
    public void setCellData(int rowNum, int colNum, String value) {
        try {
            Row row = sheet.getRow(rowNum);
            if (row == null) row = sheet.createRow(rowNum);

            Cell cell = row.getCell(colNum);
            if (cell == null) cell = row.createCell(colNum);

            cell.setCellValue(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Save and close workbook
    public void saveAndClose() {
        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            workbook.write(fos);
            fos.close();
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Get total number of rows (0-based)
    public int getRowCount() {
        return sheet.getLastRowNum() + 1;
    }

    // Get total number of columns for a specific row
    public int getColumnCount(int rowNum) {
        Row row = sheet.getRow(rowNum);
        return row != null ? row.getLastCellNum() : 0;
    }
}





