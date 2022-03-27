package com.warehousing.aquarium.service.impl;

import com.warehousing.aquarium.entity.*;
import com.warehousing.aquarium.model.request.ImportRequest;
import com.warehousing.aquarium.model.request.ProductImportRequest;
import com.warehousing.aquarium.repository.*;
import com.warehousing.aquarium.service.ImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ImportServiceImpl implements ImportService {
    ImportRepository importRepository;

    ProductBranchRepository productBranchRepository;

    ProductRepository productRepository;

    BranchRepository branchRepository;

    SupplierRepository supplierRepository;

    PaymentTypeRepository paymentTypeRepository;

    @Autowired
    public ImportServiceImpl(ImportRepository importRepository, ProductBranchRepository productBranchRepository,
                             ProductRepository productRepository, BranchRepository branchRepository,
                             SupplierRepository supplierRepository, PaymentTypeRepository paymentTypeRepository) {
        this.importRepository = importRepository;
        this.productBranchRepository = productBranchRepository;
        this.productRepository = productRepository;
        this.branchRepository = branchRepository;
        this.supplierRepository = supplierRepository;
        this.paymentTypeRepository = paymentTypeRepository;
    }


    @Override
    public boolean addListImport(ImportRequest importRequest) {
        List<ProductBranchEntity> productBranchEntities = new ArrayList<>();
        ImportEntity importEntity = new ImportEntity();
        Optional<BranchEntity> branchEntity = branchRepository.findById(importRequest.getBranchId());
        Optional<SupplierEntity> supplierEntity = supplierRepository.findById(importRequest.getSupplierId());
        Optional<PaymentTypeEntity> paymentTypeEntity = paymentTypeRepository.findById(importRequest.getPaymentType());
        int number = 0;
        for (ProductImportRequest productImportRequest : importRequest.getProducts()) {
            number += productImportRequest.getQuantity();
            ProductBranchEntity productBranchEntity = new ProductBranchEntity();
            Optional<ProductEntity> productEntity = productRepository.findById(productImportRequest.getProductId());
            productEntity.ifPresent(entity -> productBranchEntity.setProductID(entity.getProductId()));
            productBranchEntity.setQuantityOnHand(productImportRequest.getQuantity());
            productBranchEntity.setSaleQuantity(productImportRequest.getSaleQuantity());
            branchEntity.ifPresent(entity -> productBranchEntity.setBranchID(entity.getBranchId()));
            productBranchEntities.add(productBranchEntity);
        }
        importEntity.setImportTime(new Date());
        importEntity.setImportPrice(importRequest.getImportPrice());
        importEntity.setNumberImport((long) number);
        supplierEntity.ifPresent(entity -> importEntity.setSupplierID(entity.getSupplierId()));
        paymentTypeEntity.ifPresent(typeEntity -> importEntity.setPaymentID(typeEntity.getPaymentID()));
        branchEntity.ifPresent(entity -> importEntity.setProBranchID(entity.getBranchId()));
        importEntity.setUserID(importRequest.getEmployee());
        importEntity.setStatus(importRequest.getStatusImport());
        importEntity.setStatusPayment(importRequest.getStatusPayment());
        importEntity.setSttStore(importRequest.getStatusStore());
        try {
            importRepository.save(importEntity);
            productBranchRepository.saveAll(productBranchEntities);
            return true;

        } catch (Exception exception) {
            return false;
        }
    }
}
