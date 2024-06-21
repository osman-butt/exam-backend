package kea.exam.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DisciplineResponseDto {
    private Long id;
    private String name;
    private String resultType;
    private String measurementType;
}
