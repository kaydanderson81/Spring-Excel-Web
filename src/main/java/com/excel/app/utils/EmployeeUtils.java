package com.excel.app.utils;

import com.excel.app.model.Employee;
import com.excel.app.model.Project;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.excel.app.utils.ExcelUtils.sheet;
import static com.excel.app.utils.ExcelUtils.workbook;
import static com.excel.app.utils.ProjectUtils.getProjectByEmployeeName;

public class EmployeeUtils {

    public static List<Employee> getEmployeeInformation() throws IOException, ParseException {
        List<Employee> employees = new ArrayList<>();

        for (int i=10; i <= getEmployeeRowsCount(); i++) {
            Employee employee = new Employee();
            String name = (String) ExcelUtils.getCellData(i, 0);
            List<Project> projects = getProjectByEmployeeName(name);

            String contractFrom = getEmployeeContractedDateFrom(i);
            String contractTo = getEmployeeContractedDateTo(i);

            employee.setName(name);
            employee.setContractFrom(contractFrom);
            employee.setContractTo(contractTo);

            if (name.contains("50%")) {
                for (Project project : projects) {
                    Number projectsMonths = project.getProjectMonths();
                    double halfMonths = projectsMonths.doubleValue() / 2.0;
                    project.setProjectMonths(halfMonths);
                }

            }
            employee.setProjects(projects);
            employees.add(employee);
        }


        return employees;
    }

    public static String getEmployeeContractedDateFrom(int rowNum) {
        String contractMonthFrom = (String) ExcelUtils. getCellData(rowNum, 1);
        String contractYearFrom = (String) ExcelUtils. getCellData(rowNum, 2);
        if (contractMonthFrom.contains(".")) {
            return contractMonthFrom + "20" + contractYearFrom;
        } if (!contractMonthFrom.isEmpty() && !contractYearFrom.isEmpty()) {
            return "01." + contractMonthFrom + ".20" + contractYearFrom;
        } else {
            return "";
        }
    }

    public static String getEmployeeContractedDateTo(int rowNum) {
        String contractMonthTo = (String) ExcelUtils. getCellData(rowNum, 3);
        String contractYearTo = (String) ExcelUtils. getCellData(rowNum, 4);
        if (contractMonthTo.contains(".")) {
            return contractMonthTo + "20" + contractYearTo;
        }
        if (!contractMonthTo.isEmpty() && !contractMonthTo.isBlank()) {
            return "01." + contractMonthTo + ".20" + contractYearTo;
        }else {
            return "No date entered";
        }
    }

    public static int getEmployeeRowsCount() {
        DataFormatter formatter = new DataFormatter();
        FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
        String cellValue;
        int count = 0;
        int rowIndex = 10;
        for (Row row : sheet) {
            for (Cell cell : row) {
                cellValue = formatter.formatCellValue(cell, evaluator);
                if (row.getRowNum() == rowIndex && !cellValue.isEmpty() && !Objects.equals(cellValue, "0,0")) {
                    count += 1;
                    rowIndex +=1;
                }
            }
        }
        return count + 9;
    }
}
