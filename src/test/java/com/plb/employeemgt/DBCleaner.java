package com.plb.employeemgt;

import com.plb.employeemgt.repository.UserRepository;
import com.plb.employeemgt.repository.VinylRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DBCleaner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VinylRepository vinylRepository;

    public void clearAllTables() {
        vinylRepository.deleteAll();
        userRepository.deleteAll();
    }
}
