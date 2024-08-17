package dev.martin1500.controller;

import dev.martin1500.exception.ResourceNotFoundException;
import dev.martin1500.model.Dinosaur;
import dev.martin1500.service.DinosaurServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/dinosaurs")
public class DinosaurController {

    private final DinosaurServiceImpl dinosaurService;

    @Autowired
    public DinosaurController(DinosaurServiceImpl dinosaurService) {
        this.dinosaurService = dinosaurService;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Dinosaur dinosaur) {
        dinosaurService.create(dinosaur);
        return ResponseEntity.status(HttpStatus.OK).body(dinosaur);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        try{
            Dinosaur dinosaur = dinosaurService.get(id);
            return ResponseEntity.status(HttpStatus.OK).body(dinosaur);
        }catch (ResourceNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping("/period")
    public ResponseEntity<?> findByPeriod(@RequestParam String period) {
        return null;
    }

    @GetMapping("/diet")
    public ResponseEntity<?> findByDiet(@RequestParam String diet) {
        return null;
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateDinosaur(@PathVariable Long id, @Valid @RequestBody Dinosaur dinosaur){
        try{
            Dinosaur updated = dinosaurService.update(dinosaur,id);
            return ResponseEntity.status(HttpStatus.OK).body(updated);
        }catch (ResourceNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}