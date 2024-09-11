package org.example.repository.inter;

import org.example.entity.Manager;
import java.util.List;
import java.util.Optional;

public interface ManagerRepository {

    // Create
    void register(Manager manager);

    // Read
    Optional<Manager> findById(Integer id);
    Optional<Manager> findByEmail(String email);
    List<Manager> findAll();

    // Update
    void update(Manager manager);

    // Delete
    void deleteById(Integer id);
}
