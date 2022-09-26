package com.excel.app.controller;

import com.excel.app.model.Employee;
import com.excel.app.model.Project;
import com.excel.app.model.SheetYear;
import com.excel.app.utils.EmployeeUtils;
import com.excel.app.utils.ExcelUtils;
import com.excel.app.utils.ProjectUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.text.ParseException;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/ines/excel")
public class EmployeeController {

    String excelPath = "./data/22_07_20__Mitarbeiterplanung_InES_BH.xlsx";
    String sheet = Year.now().format(DateTimeFormatter.ofPattern("yyyy"));


    @GetMapping("/home")
    public String showHomePage() {
        return "/ines/home.html";
    }

    @GetMapping("/employees")
    public String showEmployeeList(Model model) throws ParseException, IOException {
        new ExcelUtils(this.excelPath, this.sheet);
        List<Employee> employees = EmployeeUtils.getEmployeeInformation();
        model.addAttribute("employees", employees);
        return "/ines/employee.html";
    }

    @GetMapping("/projects")
    public String showProjectList(Model model) {
        new ExcelUtils(this.excelPath, this.sheet);
        List<Project> projects = ProjectUtils.getListOfProjects();
        model.addAttribute("projects", projects);
        return "/ines/projects.html";
    }

    @GetMapping("/chart")
    public String reloadChartWithYear(Model model) throws IOException, ParseException {
        new ExcelUtils(this.excelPath, this.sheet);
        List<Employee> employees = EmployeeUtils.getEmployeeInformation();

        List<String> sheetYears = ExcelUtils.getStringSheetYears();
        model.addAttribute("employees", employees);
        model.addAttribute("sheetYears", sheetYears);
        return "/ines/chart.html";

    }

    @PostMapping("/chart")
    public String reloadChartWithYear(@ModelAttribute("sheetYear") SheetYear sheetYear,
                                      Model model) throws IOException, ParseException {
        new ExcelUtils(this.excelPath, sheetYear.getYear());
        List<Employee> employees = EmployeeUtils.getEmployeeInformation();

        List<String> sheetYears = ExcelUtils.getStringSheetYears();
        model.addAttribute("currentYear", sheetYear.getYear());
        model.addAttribute("employees", employees);
        model.addAttribute("sheetYears", sheetYears);
        return "/ines/chart.html";

    }

    public static void main(String[] args) throws IOException, ParseException {
        new ExcelUtils("./data/22_07_20__Mitarbeiterplanung_InES_BH.xlsx", "2022");
        List<Project> projects = ProjectUtils.getListOfProjects();
        System.out.println("Projects: " + projects);


    }

}

