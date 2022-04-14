package com.warehousing.aquarium.service.impl;

import com.warehousing.aquarium.entity.*;
import com.warehousing.aquarium.mapper.ImportMapper;
import com.warehousing.aquarium.model.request.ImportRequest;
import com.warehousing.aquarium.model.request.ProductImportRequest;
import com.warehousing.aquarium.model.response.ImportDTO;
import com.warehousing.aquarium.model.response.ImportProductDTO;
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
    StatusRepository statusRepository;
    UserRepository userRepository;

    @Autowired
    public ImportServiceImpl(ImportRepository importRepository, ProductBranchRepository productBranchRepository,
                             ProductRepository productRepository, BranchRepository branchRepository,
                             SupplierRepository supplierRepository, PaymentTypeRepository paymentTypeRepository,
                             StatusRepository statusRepository,
                             UserRepository userRepository) {
        this.importRepository = importRepository;
        this.productBranchRepository = productBranchRepository;
        this.productRepository = productRepository;
        this.branchRepository = branchRepository;
        this.supplierRepository = supplierRepository;
        this.paymentTypeRepository = paymentTypeRepository;
        this.statusRepository = statusRepository;
        this.userRepository = userRepository;
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
            productBranchEntity.setTotalPrice(importRequest.getImportPrice());
            branchEntity.ifPresent(entity -> productBranchEntity.setBranchID(entity.getBranchId()));
            productBranchEntities.add(productBranchEntity);
        }
        importEntity.setImportTime(new Date());
        importEntity.setImportPrice(importRequest.getImportPrice());
        importEntity.setNumberImport((long) number);
        importEntity.setImportPrice(importRequest.getImportPrice());
        supplierEntity.ifPresent(entity -> importEntity.setSupplierID(entity.getSupplierId()));
        paymentTypeEntity.ifPresent(typeEntity -> importEntity.setPaymentID(typeEntity.getPaymentID()));
        importEntity.setUserID(importRequest.getEmployee());
        importEntity.setStatus(importRequest.getStatusImport());
        importEntity.setStatusPayment(importRequest.getStatusPayment());
        importEntity.setSttStore(importRequest.getStatusStore());
        try {
            ImportEntity importId = importRepository.save(importEntity);
            for (ProductBranchEntity entity : productBranchEntities) {
                entity.setImportId(importId.getImportID());
            }
            productBranchRepository.saveAll(productBranchEntities);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    @Override
    public List<ImportDTO> getAllImport(int pageIndex, int pageSize, int search) {
        List<ImportEntity> importEntities = new ArrayList<>();
        if (search == 0) {
            importEntities = importRepository.findAll();
        } else {
            Optional<ImportEntity> importEntity = importRepository.findById((long) search);
            if (importEntity.isPresent()) {
                importEntities.add(importEntity.get());
            }
        }
        List<ImportDTO> list = new ArrayList<>();
        if (importEntities.isEmpty()) {
            return list;
        }
        List<StatusEntity> statusEntities = statusRepository.findAll();
        int start = pageIndex * pageSize;
        for (int i = 0; i < importEntities.size(); i++) {
            ImportEntity importEntity = importEntities.get(i);
            if (i >= start && i < start + pageSize) {
                if (i > start + pageSize) {
                    break;
                }
                BranchEntity branchEntity = new BranchEntity();
                SupplierEntity supplierEntity = new SupplierEntity();
                AccountEntity account = new AccountEntity();
                List<ProductBranchEntity> productBranchEntities;
                if (importEntity.getBranchID() != null) {
                    branchEntity = branchRepository.getOne(importEntity.getBranchID());
                }
                if (importEntity.getSupplierID() != null) {
                    supplierEntity = supplierRepository.getOne(importEntity.getSupplierID());
                }
                if (importEntity.getUserID() != null) {
                    Optional<AccountEntity> optionalAccount = userRepository.findById(importEntity.getUserID());
                    if (optionalAccount.isPresent()) {
                        account = optionalAccount.get();
                    }
                }
                productBranchEntities = productBranchRepository.findAllByImportId(importEntity.getImportID());
                List<ImportProductDTO> importProductDTOS = new ArrayList<>();
                for (ProductBranchEntity entity : productBranchEntities) {
                    ImportProductDTO importProductDTO = new ImportProductDTO();
                    importProductDTO.setProductId(entity.getProductID());
                    Optional<ProductEntity> productEntity = productRepository.findById(entity.getProductID());
                    productEntity.ifPresent(product -> importProductDTO.setProductCode(product.getProductCode()));
                    productEntity.ifPresent(product -> importProductDTO.setProductName(product.getProductName()));
                    productEntity.ifPresent(product -> importProductDTO.setSaleQuantity(product.getSaleQuantity()));
                    productEntity.ifPresent(product -> importProductDTO.setImage(product.getImage()));
                    productEntity.ifPresent(product -> importProductDTO.setColor(product.getColor()));

                    productEntity.ifPresent(product -> importProductDTO.setPrice(entity.getSaleQuantity() * product.getUnitPrice()));
                    importProductDTOS.add(importProductDTO);
                }
                ImportDTO importDTO = ImportMapper.mapImportEntityToDTO(importEntity, branchEntity, supplierEntity, account, statusEntities);
                importDTO.setListProduct(importProductDTOS);
                list.add(importDTO);
            }
        }
        return list;
    }

    @Override
    public ImportDTO getImportById(Long id) {
        Optional<ImportEntity> importEntities = importRepository.findById(id);
        List<StatusEntity> statusEntities = statusRepository.findAll();
        if (!importEntities.isPresent()) {
            return null;
        }
        BranchEntity branchEntity = new BranchEntity();
        SupplierEntity supplierEntity = new SupplierEntity();
        AccountEntity account = new AccountEntity();
        List<ProductBranchEntity> productBranchEntities;
        if (importEntities.get().getBranchID() != null) {
            branchEntity = branchRepository.getOne(importEntities.get().getBranchID());
        }
        if (importEntities.get().getSupplierID() != null) {
            supplierEntity = supplierRepository.getOne(importEntities.get().getSupplierID());
        }
        if (importEntities.get().getUserID() != null) {
            account = userRepository.getOne(importEntities.get().getUserID());
        }
        productBranchEntities = productBranchRepository.findAllByImportId(importEntities.get().getImportID());
        List<ImportProductDTO> importProductDTOS = new ArrayList<>();
        for (ProductBranchEntity entity : productBranchEntities) {
            ImportProductDTO importProductDTO = new ImportProductDTO();
            importProductDTO.setProductId(entity.getProductID());
            Optional<ProductEntity> productEntity = productRepository.findById(entity.getProductID());
            productEntity.ifPresent(product -> importProductDTO.setProductCode(product.getProductCode()));
            productEntity.ifPresent(product -> importProductDTO.setProductName(product.getProductName()));
            productEntity.ifPresent(product -> importProductDTO.setSaleQuantity(product.getSaleQuantity()));
            productEntity.ifPresent(product -> importProductDTO.setImage(product.getImage()));
            productEntity.ifPresent(product -> importProductDTO.setColor(product.getColor()));
            productEntity.ifPresent(product -> importProductDTO.setQuantity(entity.getQuantityOnHand()));
            importEntities.ifPresent(product -> importProductDTO.setPrice(product.getImportPrice()));
            productEntity.ifPresent(product -> importProductDTO.setPrice(entity.getQuantityOnHand() * product.getUnitPrice()));
            importProductDTOS.add(importProductDTO);
        }
        ImportDTO importDTO = ImportMapper.mapImportEntityToDTO(importEntities.get(), branchEntity, supplierEntity, account, statusEntities);
        importDTO.setListProduct(importProductDTOS);
        return importDTO;
    }
}
