package com.warehousing.aquarium.service.impl;

import com.warehousing.aquarium.entity.CustomerEntity;
import com.warehousing.aquarium.entity.ExportEntity;
import com.warehousing.aquarium.entity.ProductBranchEntity;
import com.warehousing.aquarium.entity.ProductEntity;
import com.warehousing.aquarium.model.request.ExportRequest;
import com.warehousing.aquarium.model.request.ProductImportRequest;
import com.warehousing.aquarium.repository.CustomerRepository;
import com.warehousing.aquarium.repository.ExportRepository;
import com.warehousing.aquarium.repository.ImportRepository;
import com.warehousing.aquarium.repository.ProductRepository;
import com.warehousing.aquarium.service.ExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class ExportServiceImpl implements ExportService {
    ImportRepository importRepository;
    ExportRepository exportRepository;
    ProductRepository productRepository;
    CustomerRepository customerRepository;

    @Autowired
    public ExportServiceImpl(ImportRepository importRepository, ExportRepository exportRepository, ProductRepository productRepository, CustomerRepository customerRepository) {
        this.importRepository = importRepository;
        this.exportRepository = exportRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }


    @Override
    public Boolean addExport(ExportRequest exportRequest) {
        int number = 0;
        for (ProductImportRequest productExportRequest : exportRequest.getProducts()) {
            number += productExportRequest.getSaleQuantity();
            Optional<ProductEntity> productEntity = productRepository.findById(productExportRequest.getProductId());
            if (productEntity.isPresent()) {
                ProductEntity entity = productEntity.get();
                entity.setStockQuantity((int) (entity.getStockQuantity() - productExportRequest.getSaleQuantity()));
                entity.setSaleQuantity((int) (entity.getSaleQuantity() - productExportRequest.getSaleQuantity()));
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
        exportEntity.setExportPrice(exportEntity.getExportPrice());
        exportEntity.setNumberExport((long) number);
        exportEntity.setTaxID(exportEntity.getTaxID());
        exportEntity.setUserID(exportEntity.getUserID());
        exportEntity.setCustomerID(exportEntity.getCustomerID());
        exportEntity.setStatus(exportEntity.getStatus());
        exportEntity.setStatusPayment(exportEntity.getStatusPayment());
        exportRepository.save(exportEntity);
        return true;
    }
}
