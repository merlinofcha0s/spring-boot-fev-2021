package com.plb.employeemgt.service;

import com.plb.employeemgt.entity.Vinyl;
import com.plb.employeemgt.repository.VinylRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VinylService {

    private final VinylRepository vinylRepository;

    public VinylService(VinylRepository vinylRepository) {
        this.vinylRepository = vinylRepository;
    }

    public List<Vinyl> getVinylsByUser(String email) {
        return vinylRepository.findAllByUserEmail(email);
    }
}
