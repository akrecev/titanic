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

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "p_class", nullable = false)
    private PClass pClass;

    @Column(nullable = false)
    private Boolean survived;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Sex sex;

    private Integer age;

    @Column(name = "siblings_spouses")
    private Integer siblingsSpousesAboard;

    @Column(name = "parents_children")
    private Integer parentsChildrenAboard;

    private Double fare;
}
