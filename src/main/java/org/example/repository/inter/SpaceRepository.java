package org.example.repository.inter;

import org.example.entity.Manager;
import org.example.entity.Space;

import java.util.List;
import java.util.Optional;

public interface SpaceRepository {

    // Create
    Space save(Space space);

    // Read
    Optional<Space> findById(Integer space_id);
    List<Space> findAll();

    // Update
    Space update(Space space);

    boolean deletById(Integer space_id);

    List<Space> search(String data);
}
