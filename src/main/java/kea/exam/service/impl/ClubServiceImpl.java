package kea.exam.service.impl;

import kea.exam.entity.Club;
import kea.exam.exception.NotFoundException;
import kea.exam.repository.ClubRepository;
import kea.exam.service.ClubService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClubServiceImpl implements ClubService {

    private final ClubRepository clubRepository;

    public ClubServiceImpl(ClubRepository clubRepository) {
        this.clubRepository = clubRepository;
    }

    @Override
    public List<Club> findAll() {
        return clubRepository.findAll();
    }

    @Override
    public Club findById(Long id) {
        return clubRepository.findById(id).orElseThrow(() -> new NotFoundException("Club with id=" + id + " not found."));
    }

    @Override
    public Club findByName(String name) {
        return clubRepository.findByName(name);
    }
}
