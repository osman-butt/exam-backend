package kea.exam.dto;

import kea.exam.entity.Discipline;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParticipantResponseDto {
    private Long id;
    private String name;
    private LocalDate dateOfBirth;
    private int age;
    private String gender;
    private String clubName;
    private List<Discipline> disciplines;
}
