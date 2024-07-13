package dev.martin1500.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Entity
public class Dinosaur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ValidEnum(enumClass = DinoOrder.class, message = "The 'Order' must be SAURISCHIA, or ORNITHISCHIA.")
    @NotNull(message = "The 'Order' is required.")
    private String dinoOrder;

    @ValidEnum(enumClass = DinoSubOrder.class, message = "The 'SubOrder' must be THEROPODA, SAUROPODOMORPHA, ORNITHOPODA, MARGINOCEPHALIA, or THYREOPHORA.")
    @NotNull(message = "The 'SubOrder' is required.")
    private String dinoSubOrder;

    @ValidEnum(enumClass = GeologicalPeriod.class, message = "The 'GeologicalPeriod' must be TRIASSIC, JURASSIC, or CRETACEOUS.")
    @NotNull(message = "The 'GeologicalPeriod' is required.")
    private String geologicalPeriod;

    @NotBlank(message = "The 'Species' cannot be Blank, Empty or Null.")
    private String species;

    private String sizeWeight;
    private String physiology;
    private String habitat;
    private String diet;
    private String discovery;
}