package kea.exam.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private GenderType genderType;

    @ManyToMany
    private List<Discipline> disciplines;

    @OneToMany(mappedBy = "participant")
    private List<Result> results;

    @ManyToOne
    private Club club;
}
