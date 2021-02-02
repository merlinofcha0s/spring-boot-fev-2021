package com.plb.employeemgt.service;

import com.plb.employeemgt.entity.Job;
import com.plb.employeemgt.repository.JobRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {

    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public List<Job> getAll() {
        return jobRepository.findAll();
    }
}
