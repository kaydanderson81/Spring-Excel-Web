//package com.excel.app.service.impl;
//
//import com.excel.app.model.Employee;
//import com.excel.app.model.Project;
//import com.excel.app.repository.EmployeeRepository;
//import com.excel.app.service.EmployeeService;
//import com.excel.app.utils.ExcelUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.io.IOException;
//
//public class EmployeeServiceImpl implements EmployeeService {
//    @Autowired
//    private EmployeeRepository employeeRepository;
//
//    @Override
//    public Employee getEmployeeByName(String name) throws IOException {
//        String excelPath = "./data/22_07_20__Mitarbeiterplanung_InES_BH.xlsx";
//        String sheetName = "2022";
//        ExcelUtils excel = new ExcelUtils(excelPath, sheetName);
//
//        ExcelUtils.getRowCount();
//        String employeeName = (String) ExcelUtils.getCellData(11, 0);
//        Project projectName = (Project) ExcelUtils.getCellData(5, 11);
//        String contractFrom = (String) ExcelUtils.getCellData(11, 1);
//        String contractTo = (String) ExcelUtils.getCellData(11, 2);
//
//        Employee employee = new Employee();
//        employee.setName(employeeName);
//        employee.setProjectName(projectName);
//        employee.setContractFrom(contractFrom);
//        employee.setContractTo(contractTo);
//
//        return employee;
//
//    }
//}
