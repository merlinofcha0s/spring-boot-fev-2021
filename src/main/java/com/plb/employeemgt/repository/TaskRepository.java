package com.plb.employeemgt.repository;

import com.plb.employeemgt.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
