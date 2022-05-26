package com.warehousing.aquarium.service.impl;

import com.warehousing.aquarium.entity.AccountEntity;
import com.warehousing.aquarium.entity.SupplierEntity;
import com.warehousing.aquarium.model.response.SupplierDTO;
import com.warehousing.aquarium.repository.SupplierRepository;
import com.warehousing.aquarium.repository.UserRepository;
import com.warehousing.aquarium.service.SupplierService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SupplierServiceImpl implements SupplierService {

    SupplierRepository supplierRepository;

    UserRepository userRepository;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository, UserRepository userRepository) {
        this.supplierRepository = supplierRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<SupplierDTO> getAllSupplier(int pageIndex, int pageSize) {
        List<SupplierDTO> supplierDTOS = new ArrayList<>();
        List<SupplierEntity> supplierEntities = supplierRepository.findAll(PageRequest.of(pageIndex, pageSize)).getContent();
        for (SupplierEntity supplierEntity : supplierEntities) {
            SupplierDTO supplierDTO = new SupplierDTO();
            supplierDTO.setSupplierId(supplierEntity.getSupplierId());
            supplierDTO.setSupplierCode(supplierEntity.getSupplierCode());
            supplierDTO.setSupplierName(supplierEntity.getSupplierName());
            supplierDTO.setEmail(supplierEntity.getEmail());
            supplierDTO.setGroup(supplierEntity.getGroup());
            supplierDTO.setPhone(supplierEntity.getPhone());
            supplierDTO.setStatus(supplierEntity.isStatus());
            supplierDTO.setTaxIdentificationNumber(supplierEntity.getTaxIdentificationNumber());
            supplierDTO.setDept(supplierEntity.getDept());
            supplierDTO.setAddress(supplierEntity.getAddress());
            if (supplierEntity.getUserId() != null && userRepository.findById(supplierEntity.getUserId()).isPresent()) {
                AccountEntity account = userRepository.findById(supplierEntity.getUserId()).get();
                supplierDTO.setUser(account.getName());
            }
            supplierDTO.setDescription(supplierEntity.getDescription());
            supplierDTOS.add(supplierDTO);

        }
        return supplierDTOS;
    }

    @Override
    public SupplierEntity addNewSupplier(SupplierDTO supplierDTO) {
        if (supplierRepository.findAllByEmailOrPhone(supplierDTO.getEmail(), supplierDTO.getPhone()) != null) {
            throw new RuntimeException("Email or Phone existed");
        }

        try {
            ModelMapper mapper = new ModelMapper();
            SupplierEntity entity = mapper.map(supplierDTO, SupplierEntity.class);
            return supplierRepository.save(entity);
        } catch (Exception exception) {
            return null;
        }
    }

    @Override
    public Boolean deleteSupplier(Long id) {
        try {
            supplierRepository.deleteById(id);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    @Override
    public SupplierDTO getSupplierById(Long id) {
        Optional<SupplierEntity> optionalSupplier = supplierRepository.findById(id);
        SupplierDTO supplierDTO = new SupplierDTO();

        if (optionalSupplier.isPresent()) {
            SupplierEntity supplierEntity = optionalSupplier.get();
            supplierDTO.setSupplierId(supplierEntity.getSupplierId());
            supplierDTO.setSupplierCode(supplierEntity.getSupplierCode());
            supplierDTO.setSupplierName(supplierEntity.getSupplierName());
            supplierDTO.setEmail(supplierEntity.getEmail());
            supplierDTO.setGroup(supplierEntity.getGroup());
            supplierDTO.setPhone(supplierEntity.getPhone());
            supplierDTO.setStatus(supplierEntity.isStatus());
            supplierDTO.setTaxIdentificationNumber(supplierEntity.getTaxIdentificationNumber());
            supplierDTO.setDept(supplierEntity.getDept());
            supplierDTO.setAddress(supplierEntity.getAddress());
            if (supplierEntity.getUserId() != null && userRepository.findById(supplierEntity.getUserId()).isPresent()) {
                AccountEntity account = userRepository.findById(supplierEntity.getUserId()).get();
                supplierDTO.setUser(account.getName());
            }
            supplierDTO.setDescription(supplierEntity.getDescription());
        }
        return supplierDTO;
    }
}
