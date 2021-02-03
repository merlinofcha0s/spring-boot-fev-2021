package com.plb.employeemgt;

import com.plb.employeemgt.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DBCleaner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VinylRepository vinylRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private TaskRepository taskRepository;

    public void clearAllTables() {
        vinylRepository.deleteAll();
        userRepository.deleteAll();
        jobRepository.deleteAll();
        taskRepository.deleteAll();
        employeeRepository.deleteAll();
    }
}
