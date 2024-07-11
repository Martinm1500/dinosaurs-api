package dev.martin1500.controller;

import dev.martin1500.model.Dinosaur;
import dev.martin1500.service.DinosaurServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/dinosaurs")
@Tag(name = "Dinosaur Controller", description = "Controller for managing dinosaur operations")
public class DinosaurController {

    private final DinosaurServiceImpl dinosaurService;

    @Autowired
    public DinosaurController(DinosaurServiceImpl dinosaurService) {
        this.dinosaurService = dinosaurService;
    }

    @PostMapping
    @Operation(summary = "Create Dinosaur", description = "Endpoint to create a new dinosaur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Dinosaur.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> create(
            @Parameter(description = "Dinosaur object to be created", required = true)
            @Valid @RequestBody Dinosaur dinosaur) {
        dinosaurService.create(dinosaur);
        return ResponseEntity.status(HttpStatus.OK).body(dinosaur);
    }
}
