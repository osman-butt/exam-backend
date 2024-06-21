package kea.exam.controller;

import kea.exam.dto.DisciplineResponseDto;
import kea.exam.service.DisciplineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/disciplines")
@CrossOrigin(origins = "http://localhost:5173")
public class DisciplineController {
    private final DisciplineService disciplineService;

    public DisciplineController(DisciplineService disciplineService) {
        this.disciplineService = disciplineService;
    }

    @GetMapping
    public ResponseEntity<List<DisciplineResponseDto>> getAllDisciplines() {
        return ResponseEntity.ok(disciplineService.findAll());
    }
}
