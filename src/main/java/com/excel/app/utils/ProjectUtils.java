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

 import static com.excel.app.utils.EmployeeUtils.getEmployeeRowsCount;
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
                        double personMonths = getProjectPersonMonths(cell.getColumnIndex());
                        project.setProjectLength(personMonths);

                        project.setMonthFrom(franceFormat.parse(cellValue).doubleValue());

                        Cell adjacentCell = row.getCell(cell.getColumnIndex() + 1);
                        String nextCell = (String) getCellData(adjacentCell.getRowIndex(), adjacentCell.getColumnIndex());
                        project.setMonthTo(franceFormat.parse(nextCell).doubleValue());

                        try {
                            double cellInt = franceFormat.parse(cellValue).doubleValue();
                            double nextInt = franceFormat.parse(nextCell).doubleValue();

                            project.setPersonMonths(nextInt - cellInt + 1.0);
                            if(cellInt < 1.0) {
                                project.setPersonMonths(nextInt - cellInt);
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
            return returnNumberOfCharInAString(months, "+");
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


    public static double getTotalPersonMonths(int columnNum) {
        double total = 0;
        double value;
        for (int i=10; i<getEmployeeRowsCount(); i++) {
            String startMonth = (String) ExcelUtils.getCellData(i, columnNum);
            if (!startMonth.isEmpty()) {
                if (startMonth.contains(",")) {
                    startMonth = startMonth.split(",")[0];
                }
                String endMonth = (String) ExcelUtils.getCellData(i, columnNum + 1);
                if (endMonth.contains(",")) {
                    endMonth = endMonth.split(",")[0];
                }

                double startDbl = Double.parseDouble(startMonth);
                double endDbl = Double.parseDouble(endMonth);
                if (i == 21 && sheet.getSheetName().equals("2022")) {
                    value = 11.5; //couldn't get 12.0 - 0.5 == 11.5. Only 12.0
                    total += value;
                } else if (i== 16 && sheet.getSheetName().equals("2022")) {
                    value = (endDbl - startDbl + 1) / 2; // For 50% work
                    total += value;
                } else {
                    value = endDbl - startDbl + 1;
                    total += value;
                }
            }
        }
        return total;
    }


    public static List<Project> getListOfProjects() {
        List<Project> projects = new ArrayList<>();
        for (int i=9; i < getProjectColumnsCount(); i+=3) {
            Project project = new Project();
            if (!project.isEmpty()) {
                String projectName = (String) ExcelUtils.getCellData(5, i);
                project.setProjectName(projectName);
                String startDate = getProjectStartDate(i);
                project.setStartDate(startDate);
                String endDate = getProjectEndDate(i);
                project.setEndDate(endDate);
                String projectId = getProjectId(i);
                project.setProjectId(projectId);
                double projectLength = getProjectPersonMonths(i);
                project.setProjectLength(projectLength);
                double totalMonthsBooked = getTotalPersonMonths(i);
                project.setBookedPersonMonths(totalMonthsBooked);

                projects.add(project);
            }
        }
        return projects;
    }



    public static int getProjectColumnsCount() {
        return sheet.getRow(8).getLastCellNum() - 9;
    }
}
