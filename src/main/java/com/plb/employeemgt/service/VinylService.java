package com.plb.employeemgt.service;

import com.plb.employeemgt.entity.Vinyl;
import com.plb.employeemgt.repository.VinylRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VinylService {

    private final VinylRepository vinylRepository;

    public VinylService(VinylRepository vinylRepository) {
        this.vinylRepository = vinylRepository;
    }

    public List<Vinyl> getVinylsByUser(String email) {
        return vinylRepository.findAllByUserEmail(email);
    }

    public Optional<Vinyl> getOne(Long id) {
        return vinylRepository.findById(id);
    }

    public List<Vinyl> getAll() {
        return vinylRepository.findAll();
    }

    public Vinyl save(Vinyl vinyl) {
        return vinylRepository.save(vinyl);
    }

    public void delete(Long id) {
        vinylRepository.deleteById(id);
    }
}
