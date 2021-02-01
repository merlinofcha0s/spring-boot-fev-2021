package com.plb.employeemgt.repository;

import com.plb.employeemgt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
