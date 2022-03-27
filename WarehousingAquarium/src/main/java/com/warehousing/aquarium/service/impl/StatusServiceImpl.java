package com.warehousing.aquarium.service.impl;

import com.warehousing.aquarium.entity.StatusEntity;
import com.warehousing.aquarium.repository.StatusRepository;
import com.warehousing.aquarium.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusServiceImpl implements StatusService {

    StatusRepository statusRepository;

    @Autowired
    public StatusServiceImpl(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    @Override
    public List<StatusEntity> getAllService() {
        return statusRepository.findAll();
    }
}
