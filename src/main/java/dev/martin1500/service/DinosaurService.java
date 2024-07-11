package dev.martin1500.service;

import dev.martin1500.model.Dinosaur;

import java.util.List;
import java.util.Optional;

public interface DinosaurService {

    List<Dinosaur> list();

    void create(Dinosaur dinosaur);

    Optional<Dinosaur> get(Long id);

    void update(Dinosaur dinosaur, Long id);

    void delete(Long id);
}
