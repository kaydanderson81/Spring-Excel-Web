package com.excel.app.utils;

import com.excel.app.model.Project;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static com.excel.app.utils.ExcelUtils.*;

public class ProjectUtils {

    public static List<Project> getProjectByEmployeeName(String name) throws IOException, ParseException {
        DataFormatter formatter = new DataFormatter();
        FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
        String cellValue;
        List<Row> rowData = getRows(sheet, formatter, evaluator, name);
        List<Project> projectList = new ArrayList<>();
        for (Row row : rowData) {
            for (Cell cell : row) {
                cellValue = formatter.formatCellValue(cell, evaluator);
                Project project = new Project();
                String projectName = (String) getCellData(5, cell.getColumnIndex());
                String projectId = getProjectId(cell.getColumnIndex());
                String start = getProjectStartDate(cell.getColumnIndex());
                String end = getProjectEndDate(cell.getColumnIndex());
                if (!projectName.isEmpty()) {
                    project.setProjectId(projectId);
                    project.setProjectName(projectName);
                    project.setStartDate(start);
                    project.setEndDate(end);
                    if (cell.getColumnIndex() > 8 && cell.getColumnIndex() < row.getPhysicalNumberOfCells() - 6 && !cellValue.isEmpty()) {

                        project.setMonthFrom(franceFormat.parse(cellValue).doubleValue());

                        Cell adjacentCell = row.getCell(cell.getColumnIndex() + 1);
                        String nextCell = (String) getCellData(adjacentCell.getRowIndex(), adjacentCell.getColumnIndex());
                        project.setMonthTo(franceFormat.parse(nextCell).doubleValue());

                        try {
                            double cellInt = franceFormat.parse(cellValue).doubleValue();
                            double nextInt = franceFormat.parse(nextCell).doubleValue();

                            project.setProjectMonths(nextInt - cellInt + 1.0);
                            if(cellInt < 1.0 || nextInt - cellInt < 1) {
                                project.setProjectMonths(nextInt - cellInt);
                            }


                        } catch (NumberFormatException nfe) {
                            System.out.println(nfe.getMessage());
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        projectList.add(project);
                    }
                }
            }
        }

        workbook.close();
        return projectList;
    }

    public static String getProjectStartDate(int columnNum) {
        String date = (String) ExcelUtils.getCellData(6, columnNum);
        if (date.contains("bis")) {
            return date.replace("bis ", "");
        }
        if (!date.contains("-")) {
            return null;
        }
        return date.split("-")[0];
    }

    public static String getProjectEndDate(int columnNum) {
        String date = (String) ExcelUtils.getCellData(6, columnNum);
        if (date.contains("bis")) {
            return date.replace("bis ", "");
        }
        if (!date.contains("-")) {
            return null;
        }
        return date.split("-")[1];
    }

    public static String getProjectId(int columnNum) {
        return (String) ExcelUtils.getCellData(7, columnNum);
    }

    public static double getProjectPersonMonths(int columnNum) {
        String months = (String) ExcelUtils.getCellData(8, columnNum + 2);
        if (months.contains("+")) {
            String newMonths = months.replace("+", " ");
            String first = newMonths.split(" ")[0];
            String second = newMonths.split(" ")[1];
            try{
                int num1 = Integer.parseInt(first);
                int num2 = Integer.parseInt(second);
                return num1 + num2;

            }
            catch (NumberFormatException ex){
                ex.printStackTrace();
            }

        }

        if (months.contains(",")) {
            try {
                NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
                Number number = format.parse(months);
                return number.doubleValue();
            } catch (NumberFormatException ex){
                ex.printStackTrace();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }

        if (months.equals("")) {
            return 0.0;
        }
        return Integer.parseInt(months);
    }

    public static List<Project> getListOfProjects() {
        List<Project> project = new ArrayList<>();
        for (int i=9; i < getProjectColumnsCount();) {
            String projectName = (String) ExcelUtils.getCellData(5, i);

            String startDate = getProjectStartDate(i);

            String endDate = getProjectEndDate(i);
            String projectId = getProjectId(i);
            double projectPersonMonths = getProjectPersonMonths(i);

            project.add(new Project(projectId, projectName, Objects.requireNonNullElse(startDate, "No date set"),
                    Objects.requireNonNullElse(endDate, "No date set"),  projectPersonMonths, null));

            return project;
        }

        return null;
    }



    public static int getProjectColumnsCount() {
        return sheet.getRow(8).getLastCellNum() - 9;
    }
}
