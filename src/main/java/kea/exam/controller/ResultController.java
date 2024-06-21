package kea.exam.controller;

import kea.exam.dto.ResultRequestDto;
import kea.exam.dto.ResultResponseDto;
import kea.exam.service.ResultService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/results")
@CrossOrigin(origins = "http://localhost:5173")
public class ResultController {

    private final ResultService resultService;

    public ResultController(ResultService resultService) {
        this.resultService = resultService;
    }

    @GetMapping
    public ResponseEntity<List<ResultResponseDto>> getAllResults() {
        return ResponseEntity.ok(resultService.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResult(@PathVariable Long id) {
        resultService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<ResultResponseDto> createResult(@RequestBody ResultRequestDto resultRequestDto) {
        return ResponseEntity.ok(resultService.create(resultRequestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResultResponseDto> updateResult(@PathVariable Long id, @RequestBody ResultRequestDto resultRequestDto) {
        return ResponseEntity.ok(resultService.update(id, resultRequestDto));
    }
}
