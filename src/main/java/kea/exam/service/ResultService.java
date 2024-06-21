package kea.exam.service;

import kea.exam.dto.ResultRequestDto;
import kea.exam.dto.ResultResponseDto;

import java.util.List;

public interface ResultService {
    List<ResultResponseDto> findAll();
    void deleteById(Long id);
    ResultResponseDto create(ResultRequestDto resultRequestDto);
    ResultResponseDto update(Long id, ResultRequestDto resultRequestDto);
}
