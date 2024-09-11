package org.example.service.impl;

import org.example.entity.Manager;
import org.example.repository.inter.ManagerRepository;
import org.example.service.inter.ManagerService;

import java.util.List;
import java.util.Optional;

public class ManagerServiceImpl implements ManagerService {

    private final ManagerRepository managerRepository;

    // Constructor Injection for dependency injection
    public ManagerServiceImpl(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    @Override
    public void register(Manager manager) {
        managerRepository.register(manager);
    }

    @Override
    public Optional<Manager> findById(Integer id) {
        return managerRepository.findById(id);
    }

    @Override
    public Optional<Manager> findByEmail(String email) {
        return managerRepository.findByEmail(email);
    }

    @Override
    public List<Manager> findAll() {
        return managerRepository.findAll();
    }

    @Override
    public void update(Manager manager) {
        managerRepository.update(manager);
    }

    @Override
    public void deleteById(Integer id) {
        managerRepository.deleteById(id);
    }
}
