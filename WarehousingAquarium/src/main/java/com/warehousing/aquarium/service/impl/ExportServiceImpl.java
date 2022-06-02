package com.warehousing.aquarium.service.impl;

import com.warehousing.aquarium.entity.*;
import com.warehousing.aquarium.model.request.ExportRequest;
import com.warehousing.aquarium.model.request.ProductImportRequest;
import com.warehousing.aquarium.model.response.ExportDTO;
import com.warehousing.aquarium.model.response.ImportProductDTO;
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
    UserRepository userRepository;
    StatusRepository statusRepository;

    @Autowired
    public ExportServiceImpl(ImportRepository importRepository, ExportRepository exportRepository, ProductRepository productRepository,
                             CustomerRepository customerRepository, WarehouseRepository warehouseRepository,
                             ProductBranchRepository productBranchRepository, BranchRepository branchRepository,
                             UserRepository userRepository, StatusRepository statusRepository) {
        this.importRepository = importRepository;
        this.exportRepository = exportRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.warehouseRepository = warehouseRepository;
        this.branchRepository = branchRepository;
        this.productBranchRepository = productBranchRepository;
        this.userRepository = userRepository;
        this.statusRepository = statusRepository;
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
            productBranchEntity.setProBranchID(productExportRequest.getProductBranchId());
            productBranchEntity.setProductBatchId(productExportRequest.getProductBatchId());
            productBranchEntities.add(productBranchEntity);
            number += productExportRequest.getSaleQuantity();
            Optional<ProductEntity> productEntity = productRepository.findById(productExportRequest.getProductId());
            if (productEntity.isPresent()) {
                ProductEntity entity = productEntity.get();
                if (entity.getSaleQuantity() <= 0) {
                    throw new RuntimeException("Quantity not enough");
                }
                if (entity.getSaleQuantity() < productExportRequest.getSaleQuantity()) {
                    entity.setSaleQuantity(0L);
                } else {
                    entity.setSaleQuantity(entity.getSaleQuantity() - productExportRequest.getSaleQuantity());
                }
                if (productExportRequest.getCanExpired() != null && productExportRequest.getCanExpired()) {
                    List<ProductBatchEntity> productBatchEntities = warehouseRepository.findAllByProductId(productExportRequest.getProductId());
                    double price = 0D;
                    double quantity = 0D;
                    for (ProductBatchEntity warehouseEntity : productBatchEntities) {
                        quantity += warehouseEntity.getQuantity();
                        price += (warehouseEntity.getQuantity() * warehouseEntity.getPrice());
                    }
                    quantity = Math.ceil(quantity * 100) / 100;
                    entity.setUnitPrice(price / quantity);
                }
                if (productExportRequest.getProductBranchId() != null) {
                    Optional<ProductBatchEntity> optionalProductBatch = warehouseRepository.findById(productExportRequest.getProductBatchId());
                    if (optionalProductBatch.isPresent()) {
                        ProductBatchEntity productBatchEntity = optionalProductBatch.get();
                        if (productBatchEntity.getQuantity() < productExportRequest.getSaleQuantity()) {
                            throw new RuntimeException("Quantity not enough");
                        }
                        productBatchEntity.setQuantity(productBatchEntity.getQuantity() - productExportRequest.getSaleQuantity());
                        warehouseRepository.save(productBatchEntity);
                    }
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
        exportEntity.setExportID(exportRequest.getExportID());
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
        List<ExportEntity> list = exportRepository.findAll(PageRequest.of(pageIndex, pageSize)).getContent();
//        Collections.sort(list, Comparator.comparing(ExportEntity::getExportTime));
        return list;
    }

    @Override
    public ExportDTO getExportById(Long id) {
        ExportDTO dto = new ExportDTO();
        Optional<ExportEntity> optional = exportRepository.findById(id);

        if (!optional.isPresent()) {
            return dto;
        }
        ExportEntity entity = optional.get();
        dto.setExportID(entity.getExportID());
        dto.setExportTime(entity.getExportTime());
        dto.setExportPrice(entity.getExportPrice());
        dto.setStatusPayment(entity.getStatusPayment());

        if (entity.getUserID() != null) {
            Optional<AccountEntity> optionalAccount = userRepository.findById(entity.getUserID());
            optionalAccount.ifPresent(accountEntity -> dto.setUser(accountEntity.getName()));
        }
        if (entity.getCustomerID() != null) {
            Optional<CustomerEntity> optionalCustomerEntity = customerRepository.findById(entity.getCustomerID());
            if (optionalCustomerEntity.isPresent()) {
                dto.setCustomerID(optionalCustomerEntity.get().getCustomerId());
                dto.setCustomerName(optionalCustomerEntity.get().getCustomerName());
            }
        }
        dto.setStatus(entity.getStatus());
        dto.setStatusPayment(entity.getStatusPayment());
        List<ProductBranchEntity> productBranchEntities = productBranchRepository.findAllByExportId(id);
        List<ImportProductDTO> listProduct = new ArrayList<>();

        for (ProductBranchEntity productBranchEntity : productBranchEntities) {
            ImportProductDTO importProductDTO = new ImportProductDTO();
            importProductDTO.setProductId(productBranchEntity.getProductID());
            Optional<ProductEntity> optionalProduct = productRepository.findById(productBranchEntity.getProductID());
            optionalProduct.ifPresent(productEntity -> importProductDTO.setProductCode(productEntity.getProductCode()));
            optionalProduct.ifPresent(productEntity -> importProductDTO.setProductName(productEntity.getProductName()));
            optionalProduct.ifPresent(productEntity -> importProductDTO.setSaleQuantity(productBranchEntity.getSaleQuantity()));
            optionalProduct.ifPresent(productEntity -> importProductDTO.setImage(productEntity.getImage()));
            optionalProduct.ifPresent(productEntity -> importProductDTO.setColor(productEntity.getColor()));
            optionalProduct.ifPresent(productEntity -> importProductDTO.setUnitPrice(productBranchEntity.getTotalPrice()));
            optionalProduct.ifPresent(productEntity -> importProductDTO.setUnitName(productEntity.getUnitName()));
            optionalProduct.ifPresent(productEntity -> importProductDTO.setCanExpired(productEntity.getCanExpired()));
            importProductDTO.setProductBranchId(productBranchEntity.getProBranchID());
            if (productBranchEntity.getProductBatchId() != null) {
                Optional<ProductBatchEntity> optionalProductBatch = warehouseRepository.findById(productBranchEntity.getProductBatchId());
                optionalProductBatch.ifPresent(importProductDTO::setProductBatch);
            }
            listProduct.add(importProductDTO);
        }
        dto.setProducts(listProduct);
        return dto;
    }

    @Override
    public Boolean deleteExportById(Long id) {
        Optional<ExportEntity> optionalExport = exportRepository.findById(id);
        if (!optionalExport.isPresent()) {
            throw new RuntimeException("Not found export");
        }
        ExportEntity exportEntity = optionalExport.get();

        List<ProductBranchEntity> productBranchEntities = productBranchRepository.findAllByExportId(exportEntity.getExportID());
        productBranchRepository.deleteAll(productBranchEntities);
        if (!productBranchEntities.isEmpty()) {
            for (ProductBranchEntity productBranchEntitie : productBranchEntities) {
                if (productBranchEntitie.getProductBatchId() == null) {
                    continue;
                }
                Optional<ProductBatchEntity> optionalProductBatch = warehouseRepository.findById(productBranchEntitie.getProductBatchId());
                if (optionalProductBatch.isPresent()) {
                    ProductBatchEntity productBatchEntity = optionalProductBatch.get();
                    productBatchEntity.setQuantity(productBatchEntity.getQuantity() + productBranchEntitie.getSaleQuantity());
                    warehouseRepository.save(productBatchEntity);
                }
                if (productBranchEntitie.getProductID() == null) {
                    continue;
                }
                Optional<ProductEntity> optionalProduct = productRepository.findById(productBranchEntitie.getProductID());
                if (optionalProduct.isPresent()) {
                    ProductEntity productEntity = optionalProduct.get();
                    productEntity.setSaleQuantity(productEntity.getSaleQuantity() + productBranchEntitie.getSaleQuantity());
                    productRepository.save(productEntity);
                }
            }
        }
        exportRepository.delete(exportEntity);
        return true;
    }

}
