package kea.exam.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultResponseDto {
    private Long id;
    private LocalDate date;
    private String resultValue;
    private ParticipantResponseDto participant;
    private DisciplineResponseDto discipline;
}
