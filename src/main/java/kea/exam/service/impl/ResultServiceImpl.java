package kea.exam.service.impl;

import kea.exam.dto.*;
import kea.exam.entity.*;
import kea.exam.exception.BadRequestException;
import kea.exam.exception.NotFoundException;
import kea.exam.repository.ResultRepository;
import kea.exam.service.DisciplineService;
import kea.exam.service.ParticipantService;
import kea.exam.service.ResultService;
import kea.exam.utils.CalculateAge;
import org.antlr.v4.runtime.CodePointBuffer;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResultServiceImpl implements ResultService {

    private final ResultRepository resultRepository;
    private final DisciplineService disciplineService;
    private final ParticipantService participantService;

    public ResultServiceImpl(ResultRepository resultRepository, DisciplineService disciplineService, ParticipantService participantService) {
        this.resultRepository = resultRepository;
        this.disciplineService = disciplineService;
        this.participantService = participantService;
    }

    @Override
    public List<ResultResponseDto> findAll() {
        return resultRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        if (!resultRepository.existsById(id)) {
            throw new NotFoundException("Result with id=" + id + " not found.");
        }
        resultRepository.deleteById(id);
    }

    @Override
    public ResultResponseDto create(ResultRequestDto resultRequestDto) {
        validateRequest(resultRequestDto);
        Result result = toEntity(resultRequestDto);
        // Get Discipline
        DisciplineResponseDto disciplineResponseDto = disciplineService.findById(resultRequestDto.getDiscipline().getId());

        // Get Participant
        ParticipantResponseDto participantResponseDto = participantService.findById(resultRequestDto.getParticipant().getId());

        // Check if the discipline is the same as the participant's discipline
        participantResponseDto.getDisciplines().stream()
                .filter(discipline -> discipline.getId().equals(disciplineResponseDto.getId()))
                .findFirst()
                .orElseThrow(() -> new BadRequestException("Participant does not compete in the discipline " + disciplineResponseDto.getName()));

        result.setParticipant(
                Participant.builder()
                        .id(participantResponseDto.getId())
                        .name(participantResponseDto.getName())
                        .genderType(GenderType.valueOf(participantResponseDto.getGender()))
                        .club(Club.builder().name(participantResponseDto.getClubName()).build())
                        .dateOfBirth(participantResponseDto.getDateOfBirth())
                        .disciplines(participantResponseDto.getDisciplines().stream().map(discipline -> Discipline.builder()
                                .id(discipline.getId())
                                .name(discipline.getName())
                                .measurementType(discipline.getMeasurementType())
                                .resultType(discipline.getResultType())
                                .build())
                                .collect(Collectors.toList()))
                        .build());

        result.setDiscipline(
                Discipline.builder()
                        .id(disciplineResponseDto.getId())
                        .name(disciplineResponseDto.getName())
                        .measurementType(MeasurementType.valueOf(disciplineResponseDto.getMeasurementType()))
                        .resultType(disciplineResponseDto.getResultType())
                        .build());
        return toDto(resultRepository.save(result));
    }

    @Override
    public ResultResponseDto update(Long id, ResultRequestDto resultRequestDto) {
        validateRequest(resultRequestDto);
        Result result = toEntity(resultRequestDto);
        Result existingResult = resultRepository.findById(id).orElseThrow(() -> new NotFoundException("Result with id=" + id + " not found."));
        existingResult.setDate(result.getDate());
        existingResult.setResultValue(result.getResultValue());
        return toDto(resultRepository.save(existingResult));
    }

    private Result toEntity(ResultRequestDto resultRequestDto) {
        return Result.builder()
                .id(resultRequestDto.getId())
                .date(resultRequestDto.getDate())
                .resultValue(resultRequestDto.getResultValue())
                .build();
    }

    private ResultResponseDto toDto(Result result) {
        System.out.println(result.getParticipant().getGenderType());
        ParticipantResponseDto participant = ParticipantResponseDto.builder()
                .id(result.getParticipant().getId())
                .name(result.getParticipant().getName())
                .dateOfBirth(result.getParticipant().getDateOfBirth())
                .disciplines(result.getParticipant().getDisciplines())
                .gender(result.getParticipant().getGenderType().toString())
                .clubName(result.getParticipant().getClub().getName())
                .age(CalculateAge.calculateAge(result.getParticipant().getDateOfBirth()))
                .build();
        DisciplineResponseDto discipline = DisciplineResponseDto.builder()
                .id(result.getDiscipline().getId())
                .name(result.getDiscipline().getName())
                .measurementType(result.getDiscipline().getMeasurementType().name())
                .resultType(result.getDiscipline().getResultType())
                .build();
        return ResultResponseDto.builder()
                .id(result.getId())
                .date(result.getDate())
                .resultValue(result.getResultValue())
                .participant(participant)
                .discipline(discipline)
                .build();
    }

    private void validateRequest(ResultRequestDto resultRequestDto) {
        StringBuilder errorMessage = new StringBuilder();
        if (resultRequestDto.getResultValue() == null || resultRequestDto.getResultValue().isEmpty()) {
            errorMessage.append("Resultat er påkrævet. ");
        }
        if (resultRequestDto.getResultValue() != null && !resultRequestDto.getResultValue().contains(":") && !resultRequestDto.getResultValue().contains(".")) {
            if (Long.parseLong(resultRequestDto.getResultValue()) < 0) {
                errorMessage.append("Resultat skal være et ikke negativt tal. ");
            }
        }
        if (resultRequestDto.getDate() == null) {
            errorMessage.append("Dato er påkrævet. ");
        }
        // Date now
        LocalDate now = LocalDate.now();
        if (resultRequestDto.getDate() != null && resultRequestDto.getDate().isAfter(now)) {
            errorMessage.append("Datoen kan ikke ligge i fremtiden. ");
        }
        if (!errorMessage.isEmpty()) {
            throw new BadRequestException(errorMessage.toString().trim());
        }
    }
}
