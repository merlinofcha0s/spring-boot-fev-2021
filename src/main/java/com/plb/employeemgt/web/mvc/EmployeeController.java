package com.plb.employeemgt.web.mvc;

import com.plb.employeemgt.entity.Employee;
import com.plb.employeemgt.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public String employees(Model model) {
        List<Employee> allEmployees = employeeService.getAll();
        model.addAttribute("employees", allEmployees);
        return "employees";
    }

    @GetMapping("/{id}")
    public String employee(@PathVariable Long id, Model model) {
        Employee employee = employeeService.getById(id).orElseThrow();
        model.addAttribute("employee", employee);
        return "employee";
    }
}
