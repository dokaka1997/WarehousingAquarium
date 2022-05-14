package com.warehousing.aquarium.service.impl;

import com.warehousing.aquarium.entity.*;
import com.warehousing.aquarium.model.request.ExportRequest;
import com.warehousing.aquarium.model.request.ProductImportRequest;
import com.warehousing.aquarium.repository.*;
import com.warehousing.aquarium.service.ExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    public ExportServiceImpl(ImportRepository importRepository, ExportRepository exportRepository, ProductRepository productRepository,
                             CustomerRepository customerRepository, WarehouseRepository warehouseRepository) {
        this.importRepository = importRepository;
        this.exportRepository = exportRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.warehouseRepository = warehouseRepository;
    }


    @Override
    public ExportEntity addExport(ExportRequest exportRequest) {
        int number = 0;
        for (ProductImportRequest productExportRequest : exportRequest.getProducts()) {
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
                    List<WarehouseEntity> warehouseEntities = warehouseRepository.findAllByProductId(productExportRequest.getProductId());
                    Double price = 0D;
                    Double quantity = 0D;
                    for (WarehouseEntity warehouseEntity : warehouseEntities) {
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
        exportRepository.save(exportEntity);
        return exportEntity;
    }
}
