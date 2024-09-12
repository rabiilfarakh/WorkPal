package org.example.repository.inter;

import org.example.entity.SpaceType;
import java.util.List;
import java.util.Optional;

public interface SpaceTypeRepository {

    // Create
    SpaceType save(SpaceType space_type);

    // Read
    Optional<SpaceType> findById(Integer manager_id);
    List<SpaceType> findAll();

    // Update
    SpaceType update(SpaceType space_type);

    boolean deletById(Integer manager_id);
}
