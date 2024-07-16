package dev.martin1500.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.martin1500.exception.ErrorMessage;
import dev.martin1500.repository.DinosaurRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
public class DinosaurControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    DinosaurRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String VALID_ORDER = String.valueOf(DinoOrder.SAURISCHIA);
    private static final String VALID_SUB_ORDER = String.valueOf(DinoSubOrder.THEROPODA);
    private static final String VALID_PERIOD = String.valueOf(GeologicalPeriod.CRETACEOUS);
    private static final String SPECIES = "Tyrannosaurus";
    private static final String SIZE_WEIGHT = "Large";
    private static final String PHYSIOLOGY = "Endothermic";
    private static final String HABITAT = "Forests";
    private static final String DIET = "Carnivore";
    private static final String DISCOVERY = "Discovered in 1902";

    //Tests for the endpoint createDinosaur
    @Test
    public void shouldCreateDinosaurWhenGivenValidDinosaur() throws Exception{

        // Arrange
        Dinosaur dinosaur = createValidDinosaur();

        String dinosaurJson = objectMapper.writeValueAsString(dinosaur);

        mockMvc.perform(post("/api/v1/dinosaurs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dinosaurJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.dinoOrder").value(VALID_ORDER))
                .andExpect(jsonPath("$.dinoSubOrder").value(VALID_SUB_ORDER))
                .andExpect(jsonPath("$.geologicalPeriod").value(VALID_PERIOD))
                .andExpect(jsonPath("$.species").value(dinosaur.getSpecies()))
                .andExpect(jsonPath("$.sizeWeight").value(SIZE_WEIGHT))
                .andExpect(jsonPath("$.physiology").value(PHYSIOLOGY))
                .andExpect(jsonPath("$.habitat").value(HABITAT))
                .andExpect(jsonPath("$.diet").value(DIET))
                .andExpect(jsonPath("$.discovery").value(DISCOVERY))
                .andExpect(header().string("Content-Type", "application/json"));
    }

    @Test
    public void shouldNotCreateDinosaurWhenGivenDinosaurWithMissingFields() throws Exception {
        // Arrange
        Dinosaur dinosaur = new Dinosaur();
        dinosaur.setDinoOrder(VALID_ORDER);
        dinosaur.setSpecies(SPECIES);

        String dinosaurJson = objectMapper.writeValueAsString(dinosaur);

        mockMvc.perform(post("/api/v1/dinosaurs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dinosaurJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errors.dinoSubOrder").value(ErrorMessage.subOrderIsRequired))
                .andExpect(jsonPath("$.errors.geologicalPeriod").value(ErrorMessage.geologicalPeriodIsRequired));
    }

    @Test
    void shouldNotCreateDinosaurWhenGivenDinosaur_InvalidField() throws Exception {
        //Arrange
        Dinosaur dinosaur = new Dinosaur();
        dinosaur.setDinoOrder("INVALID_ORDER");
        dinosaur.setDinoSubOrder(VALID_SUB_ORDER);
        dinosaur.setGeologicalPeriod(VALID_PERIOD);
        dinosaur.setSpecies(SPECIES);

        String dinosaurJson = objectMapper.writeValueAsString(dinosaur);

        mockMvc.perform(post("/api/v1/dinosaurs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dinosaurJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errors.dinoOrder").value(ErrorMessage.invalidOrder));
    }

    //Tests for the endpoint getDinosaur
    @Test
    public void shouldFindDinosaurWhenGivenValidId() throws Exception {
        Dinosaur dinosaur = createValidDinosaur();
        Long dinosaurId = 1L;

        // Mock the repository response
        given(repository.findById(dinosaurId)).willReturn(Optional.of(dinosaur));

        mockMvc.perform(get("/api/v1/dinosaurs/{id}", dinosaurId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.dinoOrder").value(VALID_ORDER))
                .andExpect(jsonPath("$.dinoSubOrder").value(VALID_SUB_ORDER))
                .andExpect(jsonPath("$.geologicalPeriod").value(VALID_PERIOD))
                .andExpect(jsonPath("$.species").value(SPECIES))
                .andExpect(jsonPath("$.sizeWeight").value(SIZE_WEIGHT))
                .andExpect(jsonPath("$.physiology").value(PHYSIOLOGY))
                .andExpect(jsonPath("$.habitat").value(HABITAT))
                .andExpect(jsonPath("$.diet").value(DIET))
                .andExpect(jsonPath("$.discovery").value(DISCOVERY));
    }

    @Test
    public void shouldNotFindDinosaurWhenGivenInvalidId() throws Exception {
        Long invalidId = 1L;

        // Mock the repository response
        given(repository.findById(invalidId)).willReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/dinosaurs/{id}", invalidId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    //Tests for the endpoint updateDinosaur
    @Test
    public void shouldUpdateDinosaurWhenGivenValidDinosaur() throws Exception {
        // Arrange
        Long validId = 1L;
        Dinosaur validDinosaur = createValidDinosaur();
        validDinosaur.setId(validId);

        String dinosaurJson = objectMapper.writeValueAsString(validDinosaur);

        // Mock the repository response
        given(repository.existsById(validId)).willReturn(true);
        given(repository.save(validDinosaur)).willReturn(validDinosaur);

        mockMvc.perform(put("/api/v1/dinosaurs/{id}", validId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dinosaurJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.dinoOrder").value(VALID_ORDER))
                .andExpect(jsonPath("$.dinoSubOrder").value(VALID_SUB_ORDER))
                .andExpect(jsonPath("$.geologicalPeriod").value(VALID_PERIOD))
                .andExpect(jsonPath("$.species").value(SPECIES))
                .andExpect(jsonPath("$.sizeWeight").value(SIZE_WEIGHT))
                .andExpect(jsonPath("$.physiology").value(PHYSIOLOGY))
                .andExpect(jsonPath("$.habitat").value(HABITAT))
                .andExpect(jsonPath("$.diet").value(DIET))
                .andExpect(jsonPath("$.discovery").value(DISCOVERY));
    }

    @Test
    public void shouldNotUpdateDinosaurWhenGivenDinosaurWithInvalidField() throws Exception {
        // Arrange
        Long validId = 1L;
        Dinosaur dinosaur = new Dinosaur();
        dinosaur.setDinoOrder("INVALID_ORDER");
        dinosaur.setDinoSubOrder(VALID_SUB_ORDER);
        dinosaur.setGeologicalPeriod(VALID_PERIOD);
        dinosaur.setSpecies(SPECIES);

        String dinosaurJson = objectMapper.writeValueAsString(dinosaur);

        // Mock the repository response
        given(repository.existsById(validId)).willReturn(true);

        mockMvc.perform(put("/api/v1/dinosaurs/{id}", validId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dinosaurJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errors.dinoOrder").value(ErrorMessage.invalidOrder));
    }

    @Test
    public void shouldNotUpdateDinosaurWhenGivenDinosaurWithMissingField() throws Exception {
        // Arrange
        Long validId = 1L;
        Dinosaur dinosaur = new Dinosaur();
        dinosaur.setDinoOrder(VALID_ORDER);
        dinosaur.setSpecies(SPECIES);

        String dinosaurJson = objectMapper.writeValueAsString(dinosaur);

        // Mock the repository response
        given(repository.existsById(validId)).willReturn(true);

        mockMvc.perform(put("/api/v1/dinosaurs/{id}", validId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dinosaurJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errors.dinoSubOrder").value(ErrorMessage.subOrderIsRequired))
                .andExpect(jsonPath("$.errors.geologicalPeriod").value(ErrorMessage.geologicalPeriodIsRequired));
    }

    @Test
    public void shouldNotUpdateDinosaurWhenGivenInvalidId() throws Exception {
        // Arrange
        Long invalidId = 999L;
        Dinosaur validDinosaur = createValidDinosaur();
        validDinosaur.setId(invalidId);

        String dinosaurJson = objectMapper.writeValueAsString(validDinosaur);

        // Mock the repository response
        given(repository.existsById(invalidId)).willReturn(false);

        mockMvc.perform(put("/api/v1/dinosaurs/{id}", invalidId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dinosaurJson))
                .andExpect(status().isNotFound());
    }

    private Dinosaur createValidDinosaur() {
        Dinosaur dinosaur = new Dinosaur();
        dinosaur.setDinoOrder(VALID_ORDER);
        dinosaur.setDinoSubOrder(VALID_SUB_ORDER);
        dinosaur.setGeologicalPeriod(VALID_PERIOD);
        dinosaur.setSpecies(SPECIES);
        dinosaur.setSizeWeight(SIZE_WEIGHT);
        dinosaur.setPhysiology(PHYSIOLOGY);
        dinosaur.setHabitat(HABITAT);
        dinosaur.setDiet(DIET);
        dinosaur.setDiscovery(DISCOVERY);
        return dinosaur;
    }
}