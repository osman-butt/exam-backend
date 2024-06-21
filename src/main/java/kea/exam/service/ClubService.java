package kea.exam.service;

import kea.exam.entity.Club;

import java.util.List;

public interface ClubService {
    List<Club> findAll();
    Club findById(Long id);
    Club findByName(String name);
}
