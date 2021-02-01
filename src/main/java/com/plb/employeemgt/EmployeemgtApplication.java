package com.plb.employeemgt;

import com.plb.employeemgt.entity.*;
import com.plb.employeemgt.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.StandardEnvironment;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@SpringBootApplication
public class EmployeemgtApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(EmployeemgtApplication.class);
        ConfigurableEnvironment environment = new StandardEnvironment();
        environment.setDefaultProfiles("dev");
        application.setEnvironment(environment);
        application.run(args);
    }


    @Bean
    public CommandLineRunner createData(UserRepository userRepository,
                                        VinylRepository vinylRepository,
                                        EmployeeRepository employeeRepository,
                                        JobRepository jobRepository,
                                        TaskRepository taskRepository) {
        return args -> {
            User newUser = new User();
            newUser.setEmail("toto@toto.com");
            newUser.setFirstName("toto");
            newUser.setLastName("tata");

            userRepository.save(newUser);

            Vinyl inTheEnd = new Vinyl("In the end", LocalDate.of(2000, 10, 24), newUser);
            Vinyl papercut = new Vinyl("Papercut", LocalDate.of(2000, 10, 24), newUser);
            Vinyl oneStepCloser = new Vinyl("One step closer", LocalDate.of(2000, 10, 24), newUser);
            Vinyl pointsOfAuthority = new Vinyl("Points of Authority", LocalDate.of(2000, 10, 24), newUser);

            inTheEnd.setUser(newUser);
            papercut.setUser(newUser);
            oneStepCloser.setUser(newUser);
            pointsOfAuthority.setUser(newUser);

            vinylRepository.save(inTheEnd);
            vinylRepository.save(papercut);
            vinylRepository.save(oneStepCloser);
            vinylRepository.save(pointsOfAuthority);

            newUser.getVinyls().add(inTheEnd);
            newUser.getVinyls().add(papercut);
            newUser.getVinyls().add(oneStepCloser);
            newUser.getVinyls().add(pointsOfAuthority);

            userRepository.save(newUser);

            userRepository.findAll().forEach(System.out::println);
            vinylRepository.findAll().forEach(System.out::println);

            // CREATION TP EMPLOYEE / JOB / TASK
            Employee manager = new Employee();
            manager.setSalary(3000L);
            manager.setCommissionPct(20L);
            manager.setHireDate(ZonedDateTime.now().minusYears(10).toLocalDate());

            Employee employee1 = new Employee();
            employee1.setSalary(1500L);
            employee1.setCommissionPct(10L);
            employee1.setHireDate(ZonedDateTime.now().minusYears(5).toLocalDate());
            employee1.setManager(manager);

            Employee employee2 = new Employee();
            employee2.setSalary(1500L);
            employee2.setCommissionPct(10L);
            employee2.setHireDate(ZonedDateTime.now().minusYears(3).toLocalDate());
            employee2.setManager(manager);

            Employee employee3 = new Employee();
            employee3.setSalary(1500L);
            employee3.setCommissionPct(10L);
            employee3.setHireDate(ZonedDateTime.now().minusYears(2).toLocalDate());
            employee3.setManager(manager);

            employeeRepository.save(manager);
            employeeRepository.save(employee1);
            employeeRepository.save(employee2);
            employeeRepository.save(employee3);

            Job job = new Job();
            job.setJobTitle("Developer");
            job.setMaxSalary(4000L);
            job.setMinSalary(2000L);
            job.setEmployee(employee1);

            jobRepository.save(job);

            Task task = new Task();
            task.setDescription("Faire feature A");
            task.getJobs().add(job);
            task.setTitle("Feature A");

            taskRepository.save(task);

            job.getTasks().add(task);
            jobRepository.save(job);
        };
    }
}
