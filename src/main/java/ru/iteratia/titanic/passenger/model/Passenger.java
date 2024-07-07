package ru.iteratia.titanic.passenger.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Entity
@Table(name = "passengers")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private PClass pclass;

    private Boolean survived;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    private Integer age;

    private Integer siblingsSpousesAboard;

    private Integer parentsChildrenAboard;

    private Double fare;
}
