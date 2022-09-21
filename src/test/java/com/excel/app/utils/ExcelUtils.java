//package com.excel.app.utils;
//
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.poifs.filesystem.POIFSFileSystem;
//import org.apache.poi.ss.usermodel.DataFormatter;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//
//
//public class ExcelUtils {
//
//    //for xlsx files
//    static XSSFWorkbook workbook;
//    static XSSFSheet sheet;
//
//    //for xls files
////    static HSSFWorkbook workbook;
////    static HSSFSheet sheet;
//
//    public ExcelUtils(String excelPath, String sheetName) {
//        try {
//            // for xlsx
//             workbook = new XSSFWorkbook(excelPath);
//             workbook = new XSSFWorkbook(excelPath);
//            //need inputStream for xls
////            InputStream file = new FileInputStream(excelPath);
////            workbook = new HSSFWorkbook(new POIFSFileSystem(file));
//
//            sheet = workbook.getSheet(sheetName);
//        } catch (Exception exception) {
//            System.out.println(exception.getCause());
//            System.out.println(exception.getMessage());
//            exception.printStackTrace();
//        }
//    }
//
//    public static void getCellData(int rowNum, int colNum) throws IOException {
//
//        DataFormatter formatter = new DataFormatter();
//        Object value = formatter.formatCellValue(sheet.getRow(rowNum).getCell(colNum));
//        System.out.println(value);
//    }
//
//
//    public static void getRowCount() {
//            int rowCount = sheet.getPhysicalNumberOfRows();
//            System.out.println("Number of rows: " + rowCount);
//    }
//}
