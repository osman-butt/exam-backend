package kea.exam.configuration;

import kea.exam.entity.*;
import kea.exam.repository.ClubRepository;
import kea.exam.repository.DisciplineRepository;
import kea.exam.repository.ParticipantRepository;
import kea.exam.repository.ResultRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class InitData implements CommandLineRunner {

    private final ClubRepository clubRepository;
    private final ParticipantRepository participantRepository;
    private final DisciplineRepository disciplineRepository;
    private final ResultRepository resultRepository;

    public InitData(ClubRepository clubRepository, ParticipantRepository participantRepository, DisciplineRepository disciplineRepository, ResultRepository resultRepository) {
        this.clubRepository = clubRepository;
        this.participantRepository = participantRepository;
        this.disciplineRepository = disciplineRepository;
        this.resultRepository = resultRepository;
    }
    @Override
    public void run(String... args) throws Exception {
        createData();
    }

    public void createData() {
        // Create Clubs
        Club club1 = new Club();
        club1.setName("Club 1");
        club1.setCity("City 1");

        Club club2 = new Club();
        club2.setName("Club 2");
        club2.setCity("City 2");

        Club club3 = new Club();
        club3.setName("Club 3");
        club3.setCity("City 3");

        // Save the clubs to the database
        clubRepository.save(club1);
        clubRepository.save(club2);
        clubRepository.save(club3);

        // Create diciplines
        Discipline discipline1 = new Discipline();
        discipline1.setName("Højdespring");
        discipline1.setMeasurementType(MeasurementType.valueOf("DISTANCE"));

        Discipline discipline2 = new Discipline();
        discipline2.setName("100-meterløb");
        discipline2.setMeasurementType(MeasurementType.valueOf("TIME"));

        Discipline discipline3 = new Discipline();
        discipline3.setName("Længdespring");
        discipline3.setMeasurementType(MeasurementType.valueOf("DISTANCE"));

        Discipline discipline4 = new Discipline();
        discipline4.setName("110-meter hæk");
        discipline4.setMeasurementType(MeasurementType.valueOf("TIME"));

        Discipline discipline5 = new Discipline();
        discipline5.setName("200-meterløb");
        discipline5.setMeasurementType(MeasurementType.valueOf("TIME"));

        // Save the disciplines to the database
        disciplineRepository.save(discipline1);
        disciplineRepository.save(discipline2);
        disciplineRepository.save(discipline3);
        disciplineRepository.save(discipline4);
        disciplineRepository.save(discipline5);

        // Create participants
        Participant participant1 = new Participant();
        participant1.setName("John Doe");
        participant1.setDateOfBirth(java.time.LocalDate.of(1980, 1, 1));
        participant1.setGenderType(GenderType.valueOf("MALE"));
        participant1.setClub(club1);
        participant1.setDisciplines(List.of(discipline1, discipline2));

        Participant participant2 = new Participant();
        participant2.setName("Jane Doe");
        participant2.setDateOfBirth(java.time.LocalDate.of(1993, 1, 1));
        participant2.setGenderType(GenderType.valueOf("FEMALE"));
        participant2.setClub(club2);
        participant2.setDisciplines(List.of(discipline3, discipline4));

        Participant participant3 = new Participant();
        participant3.setName("Alice Doe");
        participant3.setDateOfBirth(java.time.LocalDate.of(2008, 1, 1));
        participant3.setGenderType(GenderType.valueOf("FEMALE"));
        participant3.setClub(club3);
        participant3.setDisciplines(List.of(discipline5));

        // CREATE MORE PARTICIPANTS HERE
        Participant participant4 = new Participant();
        participant4.setName("Bob Johnson");
        participant4.setDateOfBirth(java.time.LocalDate.of(1990, 1, 1));
        participant4.setGenderType(GenderType.valueOf("MALE"));
        participant4.setClub(club3);
        participant4.setDisciplines(List.of(discipline1));

        Participant participant5 = new Participant();
        participant5.setName("Alice Johnson");
        participant5.setDateOfBirth(java.time.LocalDate.of(1995, 1, 1));
        participant5.setGenderType(GenderType.valueOf("FEMALE"));
        participant5.setClub(club2);
        participant5.setDisciplines(List.of(discipline2, discipline3));

        Participant participant6 = new Participant();
        participant6.setName("John Wick");
        participant6.setDateOfBirth(java.time.LocalDate.of(1987, 1, 1));
        participant6.setGenderType(GenderType.valueOf("MALE"));
        participant6.setClub(club1);
        participant6.setDisciplines(List.of(discipline4, discipline5));

        Participant participant7 = new Participant();
        participant7.setName("Jane Wick");
        participant7.setDateOfBirth(java.time.LocalDate.of(1996, 1, 1));
        participant7.setGenderType(GenderType.valueOf("FEMALE"));
        participant7.setClub(club3);
        participant7.setDisciplines(List.of(discipline1, discipline2));


        // SAVE
        participantRepository.save(participant1);
        participantRepository.save(participant2);
        participantRepository.save(participant3);
        participantRepository.save(participant4);
        participantRepository.save(participant5);
        participantRepository.save(participant6);
        participantRepository.save(participant7);

        // Create results
        Result result1 = new Result();
        result1.setDate(java.time.LocalDate.of(2021, 1, 1));
        result1.setResultValue("255");
        result1.setParticipant(participant1);
        result1.setDiscipline(discipline1);

        Result result2 = new Result();
        result2.setDate(java.time.LocalDate.of(2021, 1, 1));
        result2.setResultValue("10941");
        result2.setParticipant(participant1);
        result2.setDiscipline(discipline2);

        Result result3 = new Result();
        result3.setDate(java.time.LocalDate.of(2021, 1, 1));
        result3.setResultValue("500");
        result3.setParticipant(participant2);
        result3.setDiscipline(discipline3);

        Result result4 = new Result();
        result4.setDate(java.time.LocalDate.of(2021, 1, 1));
        result4.setResultValue("12721");
        result4.setParticipant(participant2);
        result4.setDiscipline(discipline4);

        Result result5 = new Result();
        result5.setDate(java.time.LocalDate.of(2021, 1, 1));
        result5.setResultValue("200");
        result5.setParticipant(participant3);
        result5.setDiscipline(discipline5);

        // CREATE MORE RESULTS HERE
        Result result6 = new Result();
        result6.setDate(java.time.LocalDate.of(2021, 1, 1));
        result6.setResultValue("255");
        result6.setParticipant(participant4);
        result6.setDiscipline(discipline1);

        Result result7 = new Result();
        result7.setDate(java.time.LocalDate.of(2021, 1, 1));
        result7.setResultValue("10941");
        result7.setParticipant(participant4);
        result7.setDiscipline(discipline2);

        Result result8 = new Result();
        result8.setDate(java.time.LocalDate.of(2021, 1, 1));
        result8.setResultValue("500");
        result8.setParticipant(participant5);
        result8.setDiscipline(discipline3);

        Result result9 = new Result();
        result9.setDate(java.time.LocalDate.of(2021, 1, 1));
        result9.setResultValue("12721");
        result9.setParticipant(participant5);
        result9.setDiscipline(discipline4);

        Result result10 = new Result();
        result10.setDate(java.time.LocalDate.of(2021, 1, 1));
        result10.setResultValue("200");
        result10.setParticipant(participant6);
        result10.setDiscipline(discipline5);

        Result result11 = new Result();
        result11.setDate(java.time.LocalDate.of(2021, 1, 1));
        result11.setResultValue("310");
        result11.setParticipant(participant7);
        result11.setDiscipline(discipline1);

        Result result12 = new Result();
        result12.setDate(java.time.LocalDate.of(2021, 1, 1));
        result12.setResultValue("10941");
        result12.setParticipant(participant7);
        result12.setDiscipline(discipline2);

        // SAVE
        resultRepository.save(result1);
        resultRepository.save(result2);
        resultRepository.save(result3);
        resultRepository.save(result4);
        resultRepository.save(result5);
        resultRepository.save(result6);
        resultRepository.save(result7);
        resultRepository.save(result8);
        resultRepository.save(result9);
        resultRepository.save(result10);
        resultRepository.save(result11);
        resultRepository.save(result12);


    }
}
