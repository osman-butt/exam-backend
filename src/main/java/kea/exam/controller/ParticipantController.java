package kea.exam.controller;

import kea.exam.dto.ParticipantRequestDto;
import kea.exam.dto.ParticipantResponseDto;
import kea.exam.service.ParticipantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/participants")
@CrossOrigin(origins = "http://localhost:5173")
public class ParticipantController {

    private final ParticipantService participantService;

    public ParticipantController(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @GetMapping
    public ResponseEntity<List<ParticipantResponseDto>> getAllParticipants(
            @RequestParam(name = "name", required = false) String name) {
                System.out.println("name: " + name);
        return ResponseEntity.ok(participantService.findAll(name));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParticipantResponseDto> getParticipantById(@PathVariable Long id) {
        return ResponseEntity.ok(participantService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParticipantById(@PathVariable Long id) {
        participantService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<ParticipantResponseDto> createParticipant(@RequestBody ParticipantRequestDto participantRequestDto) {
        return ResponseEntity.ok(participantService.create(participantRequestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParticipantResponseDto> updateParticipant(@PathVariable Long id, @RequestBody ParticipantRequestDto participantRequestDto) {
        return ResponseEntity.ok(participantService.update(id, participantRequestDto));
    }
}
