package com.plb.employeemgt.repository;

import com.plb.employeemgt.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {

}
