package com.plb.employeemgt.service;

import com.plb.employeemgt.DBCleaner;
import com.plb.employeemgt.entity.Employee;
import com.plb.employeemgt.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class EmployeeServiceTest {

    private static final Long DEFAULT_COMMISSION_PCT = 2L;
    private static final Long DEFAULT_SALARY = 3000L;
    private static final LocalDate DEFAULT_HIRE_DATE = ZonedDateTime.now().minusYears(5).toLocalDate();

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @Autowired
    private DBCleaner dbCleaner;

    private EmployeeService employeeServiceWithMockedRepository;

    @Mock
    private EmployeeRepository mockedEmployeeRepository;

    public static Employee createEntity() {
        Employee employee = new Employee();
        employee.setSalary(DEFAULT_SALARY);
        employee.setCommissionPct(DEFAULT_COMMISSION_PCT);
        employee.setHireDate(DEFAULT_HIRE_DATE);
        return employee;
    }

    @BeforeEach
    public void init() {
        employee = createEntity();
        dbCleaner.clearAllTables();
        employeeServiceWithMockedRepository = new EmployeeService(mockedEmployeeRepository);
    }

    @Test
    public void saveShouldWork() {
        Employee employeeSaved = employeeService.save(employee);

        Optional<Employee> employee = employeeRepository.findById(employeeSaved.getId());
        assertThat(employee).isPresent();
        assertThat(employee.get().getCommissionPct()).isEqualTo(DEFAULT_COMMISSION_PCT);
        assertThat(employee.get().getHireDate()).isEqualTo(DEFAULT_HIRE_DATE);
        assertThat(employee.get().getSalary()).isEqualTo(DEFAULT_SALARY);
    }

    @Test
    public void getByIdReturnEmployeeSuccessfuly() {
        // Préparation des données
        Employee employeeSaved = employeeRepository.save(employee);

        // Appel du service
        Optional<Employee> employeeId = employeeService.getById(employeeSaved.getId());

        // Vérification des données
        assertThat(employeeId).isPresent();
        assertThat(employeeId.get().getCommissionPct()).isEqualTo(DEFAULT_COMMISSION_PCT);
        assertThat(employeeId.get().getHireDate()).isEqualTo(DEFAULT_HIRE_DATE);
        assertThat(employeeId.get().getSalary()).isEqualTo(DEFAULT_SALARY);
    }

    @Test
    public void getAllEmployeesSuccessfuly() {
        employeeService.save(createEntity());
        employeeService.save(createEntity());

        List<Employee> employees = employeeService.getAll();

        assertThat(employees).isNotEmpty();
        assertThat(employees.size()).isEqualTo(2);
    }


    @Test
    public void saveShouldWorkMockedRepo() {
        Mockito.when(mockedEmployeeRepository.save(Mockito.any())).thenReturn(createEntity());

        employeeServiceWithMockedRepository.save(employee);

        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).isEmpty();
    }

    @Test
    public void deleteEmployeeSuccessfuly() {
        Employee employeeSaved = employeeService.save(employee);

        Optional<Employee> employeeById = employeeService.getById(employeeSaved.getId());
        assertThat(employeeById).isPresent();

        employeeService.delete(employeeSaved.getId());
        Optional<Employee> employeeChecked = employeeService.getById(employeeSaved.getId());
        assertThat(employeeChecked).isNotPresent();
    }
}
