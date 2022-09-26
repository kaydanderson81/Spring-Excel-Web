package com.excel.app.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class ExcelUtils {
    static XSSFWorkbook workbook;
    static XSSFSheet sheet;
    static NumberFormat franceFormat = NumberFormat.getInstance(Locale.FRANCE);

    public ExcelUtils(String excelPath, String sheetName) {
        try {
             workbook = new XSSFWorkbook(excelPath);
             workbook = new XSSFWorkbook(excelPath);
            sheet = workbook.getSheet(sheetName);


        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
    }

    public static List<Row> getRows(Sheet sheet, DataFormatter formatter, FormulaEvaluator evaluator, String searchValue) {
        List<Row> result = new ArrayList<>();
        String cellValue;
        for (Row row : sheet) {
            for (Cell cell : row) {
                cellValue = formatter.formatCellValue(cell, evaluator);
                if (cellValue.contains(searchValue)) {
                    result.add(row);
                    break;
                }
            }
        }
        return result;
    }



    public static Object getCellData(int rowNum, int colNum) {
        DataFormatter formatter = new DataFormatter();
        return formatter.formatCellValue(sheet.getRow(rowNum).getCell(colNum));
    }

    static List<Cell> getCells(Sheet sheet, DataFormatter formatter, FormulaEvaluator evaluator, String searchValue) {
        List<Cell> result = new ArrayList<>();
        String cellValue;
        for (Row row : sheet) {
            for (Cell cell : row) {
                cellValue = formatter.formatCellValue(cell, evaluator);
                if (cellValue.contains(searchValue)) {
                    result.add(cell);
                    break;
                }
            }
        }
        return result;
    }
    public static List<String> getStringSheetYears() throws IOException {
        File excelFile = new File("./data/22_07_20__Mitarbeiterplanung_InES_BH.xlsx");
        Workbook workbook = WorkbookFactory.create(excelFile);

        List<String> sheetNames = new ArrayList<>();
        for (int i=0; i<workbook.getNumberOfSheets(); i++) {
            sheetNames.add(workbook.getSheetName(i));
        }
        return sheetNames;
    }

    public static double returnNumberOfCharInAString(String string, String replace) throws NumberFormatException {
        int totalPlus = 0;
        char temp;
        for (int i = 0; i < string.length(); i++) {

            temp = string.charAt(i);
            if (temp == '+')
                totalPlus++;
        }

        String newMonths = string.replace(replace, " ");
        List<Double> monthList = new ArrayList<>();
        for (int i=0; i<= totalPlus; i++) {
            monthList.add(Double.valueOf(newMonths.split(" ")[i]));
        }
        return monthList.stream().mapToDouble(f -> f).sum();
    }

}
