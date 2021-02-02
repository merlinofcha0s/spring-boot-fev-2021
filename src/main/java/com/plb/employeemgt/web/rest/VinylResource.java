package com.plb.employeemgt.web.rest;

import com.plb.employeemgt.entity.Vinyl;
import com.plb.employeemgt.service.VinylService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vinyls")
public class VinylResource {

    private final VinylService vinylService;

    public VinylResource(VinylService vinylService) {
        this.vinylService = vinylService;
    }

    @GetMapping
    public List<Vinyl> getAll() {
        return vinylService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vinyl> getOne(@PathVariable Long id) {
        Optional<Vinyl> vinylById = vinylService.getOne(id);
        return vinylById.map(vinyl -> ResponseEntity.ok(vinyl)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Vinyl create(@RequestBody Vinyl newVinyl) {
        return vinylService.save(newVinyl);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        try {
            vinylService.delete(id);
        } catch (EmptyResultDataAccessException erdea) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vinyl not found");
        }
    }
}
