package org.example.service.impl;

import org.example.entity.Space;
import org.example.repository.inter.SpaceRepository;
import org.example.service.inter.SpaceService;

import java.util.List;
import java.util.Optional;

public class SpaceServiceImpl implements SpaceService {

    private final SpaceRepository spaceRepository;

    public SpaceServiceImpl(SpaceRepository spaceRepository) {
        this.spaceRepository = spaceRepository;
    }

    @Override
    public Space save(Space space) {
        // Delegates to repository to save the space and return the saved entity
        return spaceRepository.save(space);
    }

    @Override
    public Optional<Space> findById(Integer space_id) {
        // Delegates to repository to find a space by its ID
        return spaceRepository.findById(space_id);
    }

    @Override
    public List<Space> findAll() {
        // Delegates to repository to retrieve all spaces
        return spaceRepository.findAll();
    }

    @Override
    public Space update(Space space) {
        // Delegates to repository to update the space and return the updated entity
        return spaceRepository.update(space);
    }

    @Override
    public boolean deletById(Integer space_id) {
        // Delegates to repository to delete a space by its ID and returns the result
        return spaceRepository.deletById(space_id);
    }
}
