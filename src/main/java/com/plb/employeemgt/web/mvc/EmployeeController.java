package com.plb.employeemgt.web.mvc;

import com.plb.employeemgt.entity.Employee;
import com.plb.employeemgt.service.EmployeeService;
import com.plb.employeemgt.service.JobService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final JobService jobService;

    public EmployeeController(EmployeeService employeeService, JobService jobService) {
        this.employeeService = employeeService;
        this.jobService = jobService;
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

    @GetMapping("/new")
    public String initNewEmployee(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("jobs", jobService.getAll());
        return "new-employee";
    }

    @PostMapping
    public ModelAndView createNewEmployee(Employee newEmployee) {
        employeeService.save(newEmployee);
        return new ModelAndView("redirect:/employees");
    }
}
