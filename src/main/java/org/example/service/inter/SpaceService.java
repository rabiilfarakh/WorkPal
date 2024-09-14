package org.example.service.inter;

import org.example.entity.Space;

import java.util.List;
import java.util.Optional;

public interface SpaceService {

    // Create
    Space save(Space space);

    // Read
    Optional<Space> findById(Integer space_id);
    List<Space> findAll();

    // Update
    Space update(Space space);

    boolean deletById(Integer space_id);

    // Search
    List<Space> search(String data);
}
