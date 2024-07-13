package dev.martin1500.service;

import dev.martin1500.exception.ErrorMessage;
import dev.martin1500.exception.ResourceNotFoundException;
import dev.martin1500.model.Dinosaur;
import dev.martin1500.repository.DinosaurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DinosaurServiceImpl implements DinosaurService{

    private final DinosaurRepository dinosaurRepository;

    @Autowired
    public DinosaurServiceImpl(DinosaurRepository dinosaurRepository) {
        this.dinosaurRepository = dinosaurRepository;
    }

    @Override
    public List<Dinosaur> list() {
        return dinosaurRepository.findAll();
    }

    @Override
    public void create(Dinosaur dinosaur) {
        dinosaurRepository.save(dinosaur);
    }

    @Override
    public Dinosaur get(Long id) {
        Optional<Dinosaur> optionalDinosaur = dinosaurRepository.findById(id);
        if(optionalDinosaur.isEmpty()){
            throw new ResourceNotFoundException(ErrorMessage.ResourceNotFound);
        }
        return optionalDinosaur.get();
    }

    @Override
    public void update(Dinosaur dinosaur, Long id) {
        if(dinosaurRepository.existsById(id)){
           dinosaurRepository.save(dinosaur);
        }else{
            throw new ResourceNotFoundException(ErrorMessage.ResourceNotFound);
        }

    }

    @Override
    public void delete(Long id) {
        if(dinosaurRepository.existsById(id)){
            dinosaurRepository.deleteById(id);
        }else{
            throw new ResourceNotFoundException(ErrorMessage.ResourceNotFound);
        }
    }
}
