package org.example.service.impl;

import org.example.entity.Manager;

import org.example.repository.inter.ManagerRepository;
import org.example.service.inter.ManagerService;

public class ManagerServiceImpl implements ManagerService {

    private ManagerRepository managerRepository;

    public ManagerServiceImpl(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    @Override
    public void register(Manager manager) {
        managerRepository.register(manager);
    }
}
