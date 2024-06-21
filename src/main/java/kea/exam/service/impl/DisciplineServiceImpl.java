package kea.exam.service.impl;

import kea.exam.dto.DisciplineResponseDto;
import kea.exam.entity.Discipline;
import kea.exam.exception.NotFoundException;
import kea.exam.repository.DisciplineRepository;
import kea.exam.service.DisciplineService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DisciplineServiceImpl implements DisciplineService {

    private final DisciplineRepository disciplineRepository;

    public DisciplineServiceImpl(DisciplineRepository disciplineRepository) {
        this.disciplineRepository = disciplineRepository;
    }
    @Override
    public DisciplineResponseDto findById(Long id) {
        return toDto(disciplineRepository.findById(id).orElseThrow(() -> new NotFoundException("Discipline with id=" + id + " not found.")));
    }

    @Override
    public boolean existsById(Long id) {
        return disciplineRepository.existsById(id);
    }

    @Override
    public List<DisciplineResponseDto> findAll() {
        return disciplineRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    private DisciplineResponseDto toDto(Discipline discipline) {
        return DisciplineResponseDto.builder()
                .id(discipline.getId())
                .name(discipline.getName())
                .measurementType(discipline.getMeasurementType().name())
                .build();
    }
}
