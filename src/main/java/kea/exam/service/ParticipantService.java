package kea.exam.service;

import kea.exam.dto.ParticipantRequestDto;
import kea.exam.dto.ParticipantResponseDto;

import java.util.List;

public interface ParticipantService {
    List<ParticipantResponseDto> findAll(String name);
    ParticipantResponseDto findById(Long id);
    void deleteById(Long id);
    ParticipantResponseDto create(ParticipantRequestDto participantRequestDto);
    ParticipantResponseDto update(Long id, ParticipantRequestDto participantRequestDto);
}
