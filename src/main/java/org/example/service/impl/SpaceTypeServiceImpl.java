package org.example.service.impl;

import org.example.entity.SpaceType;
import org.example.repository.inter.SpaceTypeRepository;
import org.example.service.inter.SpaceTypeService;

import java.util.List;
import java.util.Optional;

public class SpaceTypeServiceImpl implements SpaceTypeService {

    private final SpaceTypeRepository spaceTypeRepository;

    // Injecting the repository through the constructor
    public SpaceTypeServiceImpl(SpaceTypeRepository spaceTypeRepository) {
        this.spaceTypeRepository = spaceTypeRepository;
    }

    // CREATE - Save a new SpaceType
    @Override
    public SpaceType save(SpaceType space_type) {
        return spaceTypeRepository.save(space_type);
    }

    // READ - Find a SpaceType by its ID
    @Override
    public Optional<SpaceType> findById(Integer spaceType_id) {
        return spaceTypeRepository.findById(spaceType_id);
    }

    // READ - Find all SpaceTypes
    @Override
    public List<SpaceType> findAll() {
        return spaceTypeRepository.findAll();
    }

    // UPDATE - Update an existing SpaceType
    @Override
    public SpaceType update(SpaceType space_type) {
        return spaceTypeRepository.update(space_type);
    }

    // DELETE - Delete a SpaceType by its ID
    @Override
    public boolean deletById(Integer spaceType_id) {
        return spaceTypeRepository.deletById(spaceType_id);
    }
}
