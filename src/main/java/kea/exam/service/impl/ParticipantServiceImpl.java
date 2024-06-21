package kea.exam.service.impl;

import kea.exam.dto.DisciplineResponseDto;
import kea.exam.dto.ParticipantRequestDto;
import kea.exam.dto.ParticipantResponseDto;
import kea.exam.entity.*;
import kea.exam.exception.BadRequestException;
import kea.exam.exception.NotFoundException;
import kea.exam.repository.ParticipantRepository;
import kea.exam.service.ClubService;
import kea.exam.service.DisciplineService;
import kea.exam.service.ParticipantService;
import kea.exam.utils.CalculateAge;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParticipantServiceImpl implements ParticipantService {

    private final ParticipantRepository participantRepository;
    private final ClubService clubService;
    private final DisciplineService disciplineService;

    public ParticipantServiceImpl(ParticipantRepository participantRepository, ClubService clubService, DisciplineService disciplineService) {
        this.participantRepository = participantRepository;
        this.clubService = clubService;
        this.disciplineService = disciplineService;
    }

    @Override
    public List<ParticipantResponseDto> findAll(String name) {
        if (name != null) {
            return participantRepository.findByNameContaining(name).stream().map(this::toDto).collect(Collectors.toList());
        }
        return participantRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public ParticipantResponseDto findById(Long id) {
        return toDto(participantRepository.findById(id).orElseThrow(() ->  new NotFoundException("Participant with id=" + id + " not found.")));
    }

    @Override
    public void deleteById(Long id) {
        if (!participantRepository.existsById(id)) {
            throw new NotFoundException("Participant with id=" + id + " not found.");
        }
        participantRepository.deleteById(id);
    }

    @Override
    public ParticipantResponseDto create(ParticipantRequestDto participantRequestDto) {
        validateRequest(participantRequestDto);
        Participant participant = toEntity(participantRequestDto);
        if (participantRequestDto.getDisciplines() != null) {
            for (Discipline discipline : participantRequestDto.getDisciplines()) {
                Long disciplineId = discipline.getId();
                System.out.println(disciplineId);
                if (!disciplineService.existsById(disciplineId)) {
                    throw new NotFoundException("Discipline with id=" + disciplineId + " not found.");
                }
                Discipline newDiscipline = new Discipline();
                DisciplineResponseDto disciplineResponseDto = disciplineService.findById(disciplineId);
                newDiscipline.setId(disciplineResponseDto.getId());
                newDiscipline.setName(disciplineResponseDto.getName());
                newDiscipline.setMeasurementType(MeasurementType.valueOf(disciplineResponseDto.getMeasurementType().toUpperCase()));
                participant.getDisciplines().add(newDiscipline);
            }
        }

        Club club = clubService.findByName(participantRequestDto.getClubName());
        if (club == null) {
            throw new NotFoundException("Club with name=" + participantRequestDto.getClubName() + " not found.");
        }
        participant.setClub(club);
        return toDto(participantRepository.save(participant));
    }

    @Override
    public ParticipantResponseDto update(Long id, ParticipantRequestDto participantRequestDto) {
        validateRequest(participantRequestDto);
        Participant participant = toEntity(participantRequestDto);

        Participant existingParticipant = participantRepository.findById(id).orElseThrow(() -> new NotFoundException("Participant with id=" + id + " not found."));
        existingParticipant.setName(participant.getName());
        existingParticipant.setDateOfBirth(participant.getDateOfBirth());
        existingParticipant.setGenderType(participant.getGenderType());
        existingParticipant.setDisciplines(participantRequestDto.getDisciplines());
        Club club = clubService.findByName(participantRequestDto.getClubName());
        if (club == null) {
            throw new NotFoundException("Club with name=" + participantRequestDto.getClubName() + " not found.");
        }
        existingParticipant.setClub(club);
        return toDto(participantRepository.save(existingParticipant));
    }

    private ParticipantResponseDto toDto(Participant participant) {
        return ParticipantResponseDto.builder()
                .id(participant.getId())
                .name(participant.getName())
                .dateOfBirth(participant.getDateOfBirth())
                .age(CalculateAge.calculateAge(participant.getDateOfBirth()))
                .gender(participant.getGenderType().name())
                .clubName(participant.getClub().getName())
                .disciplines(participant.getDisciplines())
                .build();
    }

    private Participant toEntity(ParticipantRequestDto participantRequestDto) {
        return Participant.builder()
                .name(participantRequestDto.getName())
                .dateOfBirth(LocalDate.parse(participantRequestDto.getDateOfBirth()))
                .genderType(GenderType.valueOf(participantRequestDto.getGender().toUpperCase()))
                .disciplines(new ArrayList<>())
                .results(new ArrayList<>())
                .club(null)
                .build();
    }

    private void validateRequest(ParticipantRequestDto participantRequestDto) {
        StringBuilder errorMessage = new StringBuilder();
        if (participantRequestDto.getName() == null || participantRequestDto.getName().isEmpty()) {
            errorMessage.append("Navn er påkrævet. ");
        }
        if (participantRequestDto.getDateOfBirth() == null || participantRequestDto.getDateOfBirth().isEmpty()) {
            errorMessage.append("Fødselsdag er påkrævet i formattet YYYY-MM-DD. ");
        }
        if (participantRequestDto.getGender() == null || participantRequestDto.getGender().isEmpty()) {
            errorMessage.append("Vælg et gyldigt køn Mand eller kvinde. ");
        }
        if (participantRequestDto.getClubName() == null || participantRequestDto.getClubName().isEmpty()) {
            errorMessage.append("Klubnavn er påkrævet. ");
        }
        if (!errorMessage.isEmpty()) {
            throw new BadRequestException(errorMessage.toString());
        }
    }
}
