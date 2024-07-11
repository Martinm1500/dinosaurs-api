package dev.martin1500.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Schema(description = "Entity representing a Dinosaur with various attributes")
public class Dinosaur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the dinosaur", example = "1")
    private Long id;

    @ValidEnum(enumClass = DinoOrder.class, message = "dinoOrder must be SAURISCHIA, or ORNITHISCHIA")
    @NotNull(message = "The 'dinoOrder' is required.")
    @Schema(description = "Order of the dinosaur", example = "SAURISCHIA")
    private String dinoOrder;

    @ValidEnum(enumClass = DinoSubOrder.class, message = "dinoSubOrder must be THEROPODA, SAUROPODOMORPHA, ORNITHOPODA, MARGINOCEPHALIA, or THYREOPHORA")
    @NotNull(message = "The 'dinoSubOrder' is required.")
    @Schema(description = "Sub-order of the dinosaur", example = "THEROPODA")
    private String dinoSubOrder;

    @ValidEnum(enumClass = GeologicalPeriod.class, message = "geologicalPeriod must be TRIASSIC, JURASSIC, or CRETACEOUS")
    @NotNull(message = "The 'geologicalPeriod' is required.")
    @Schema(description = "Geological period of the dinosaur", example = "JURASSIC")
    private String geologicalPeriod;

    @NotBlank(message = "The 'species' field cannot be Blank, Empty or Null.")
    @Schema(description = "Species of the dinosaur", example = "Tyrannosaurus Rex")
    private String species;

    @Schema(description = "Size and weight of the dinosaur", example = "Large, 7 tons")
    private String sizeWeight;

    @Schema(description = "Physiology of the dinosaur", example = "Bipedal, strong hind limbs")
    private String physiology;

    @Schema(description = "Habitat of the dinosaur", example = "Forests, swamps")
    private String habitat;

    @Schema(description = "Diet of the dinosaur", example = "Carnivorous")
    private String diet;

    @Schema(description = "Discovery details of the dinosaur", example = "Discovered in 1905 by Barnum Brown")
    private String discovery;
}