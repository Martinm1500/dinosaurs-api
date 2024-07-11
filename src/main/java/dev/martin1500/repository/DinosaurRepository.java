package dev.martin1500.repository;

import dev.martin1500.model.Dinosaur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DinosaurRepository extends JpaRepository<Dinosaur, Long> {
}
