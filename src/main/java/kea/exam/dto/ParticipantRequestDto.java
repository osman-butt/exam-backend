package kea.exam.dto;

import kea.exam.entity.Discipline;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParticipantRequestDto {
    private String name;
    private String dateOfBirth;
    private String gender;
    private String clubName;
    private List<Discipline> disciplines;
}
