package kea.exam.service;

import kea.exam.dto.DisciplineResponseDto;

import java.util.List;

public interface DisciplineService {
    DisciplineResponseDto findById(Long id);
    boolean existsById(Long id);
    List<DisciplineResponseDto> findAll();
}
