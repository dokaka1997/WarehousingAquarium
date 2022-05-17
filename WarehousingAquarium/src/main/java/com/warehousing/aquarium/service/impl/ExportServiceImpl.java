package com.warehousing.aquarium.service.impl;

import com.warehousing.aquarium.entity.*;
import com.warehousing.aquarium.model.request.ExportRequest;
import com.warehousing.aquarium.model.request.ProductImportRequest;
import com.warehousing.aquarium.repository.*;
import com.warehousing.aquarium.service.ExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ExportServiceImpl implements ExportService {
    ImportRepository importRepository;
    ExportRepository exportRepository;
    ProductRepository productRepository;
    CustomerRepository customerRepository;
    WarehouseRepository warehouseRepository;
    ProductBranchRepository productBranchRepository;
    BranchRepository branchRepository;

    @Autowired
    public ExportServiceImpl(ImportRepository importRepository, ExportRepository exportRepository, ProductRepository productRepository,
                             CustomerRepository customerRepository, WarehouseRepository warehouseRepository,
                             ProductBranchRepository productBranchRepository, BranchRepository branchRepository) {
        this.importRepository = importRepository;
        this.exportRepository = exportRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.warehouseRepository = warehouseRepository;
        this.branchRepository = branchRepository;
        this.productBranchRepository = productBranchRepository;
    }


    @Override
    public ExportEntity addExport(ExportRequest exportRequest) {
        List<ProductBranchEntity> productBranchEntities = new ArrayList<>();
        int number = 0;
        for (ProductImportRequest productExportRequest : exportRequest.getProducts()) {
            ProductBranchEntity productBranchEntity = new ProductBranchEntity();
            productBranchEntity.setSaleQuantity(productExportRequest.getSaleQuantity());
            productBranchEntity.setTotalPrice(productExportRequest.getPrice());
            productBranchEntity.setProductID(productExportRequest.getProductId());
            productBranchEntities.add(productBranchEntity);
            number += productExportRequest.getSaleQuantity();
            Optional<ProductEntity> productEntity = productRepository.findById(productExportRequest.getProductId());
            if (productEntity.isPresent()) {
                ProductEntity entity = productEntity.get();
                if (entity.getSaleQuantity() < productExportRequest.getSaleQuantity()) {
                    entity.setSaleQuantity(0);
                } else {
                    entity.setSaleQuantity((int) (entity.getSaleQuantity() - productExportRequest.getSaleQuantity()));
                }
                if (productExportRequest.getCanExpire() != null && productExportRequest.getCanExpire()) {
                    List<ProductBatchEntity> warehouseEntities = warehouseRepository.findAllByProductId(productExportRequest.getProductId());
                    Double price = 0D;
                    Double quantity = 0D;
                    for (ProductBatchEntity warehouseEntity : warehouseEntities) {
                        quantity += warehouseEntity.getQuantity();
                        price += (warehouseEntity.getQuantity() * warehouseEntity.getPrice());
                    }
                    entity.setUnitPrice(price / quantity);
                }
                productRepository.save(entity);
            }
        }
        if (!exportRequest.getStatusPayment() || exportRequest.getAmountPaid() < exportRequest.getExportPrice() && exportRequest.getCustomer() != null) {
            Optional<CustomerEntity> optionalCustomerEntity = customerRepository.findById(exportRequest.getCustomer());
            if (optionalCustomerEntity.isPresent()) {
                CustomerEntity customerEntity = optionalCustomerEntity.get();
                customerEntity.setDebt(customerEntity.getDebt() + (exportRequest.getExportPrice() - exportRequest.getAmountPaid()));
                customerRepository.save(customerEntity);
            }
        }

        ExportEntity exportEntity = new ExportEntity();
        exportEntity.setExportTime(new Date());
        exportEntity.setExportPrice(exportRequest.getExportPrice());
        exportEntity.setNumberExport((long) number);
        exportEntity.setTaxID(exportRequest.getTaxId());
        exportEntity.setUserID(exportRequest.getEmployee());
        exportEntity.setCustomerID(exportRequest.getCustomer());
        exportEntity.setStatus(exportRequest.getStatus());
        exportEntity.setStatusPayment(exportRequest.getStatusPayment());
        exportEntity = exportRepository.save(exportEntity);
        for (ProductBranchEntity productBranchEntity : productBranchEntities) {
            productBranchEntity.setExportId(exportEntity.getExportID());
        }
        productBranchRepository.saveAll(productBranchEntities);
        return exportEntity;
    }

    @Override
    public List<ExportEntity> getAllExport(int pageIndex, int pageSize) {
        return exportRepository.findAll(PageRequest.of(pageIndex, pageSize)).getContent();
    }

    @Override
    public ExportEntity getExportById(Long id) {
        Optional<ExportEntity> optional = exportRepository.findById(id);
        return optional.orElse(null);
    }


}
