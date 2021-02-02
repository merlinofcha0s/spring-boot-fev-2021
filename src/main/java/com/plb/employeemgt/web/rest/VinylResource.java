package com.plb.employeemgt.web.rest;

import com.plb.employeemgt.entity.Vinyl;
import com.plb.employeemgt.service.VinylService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
