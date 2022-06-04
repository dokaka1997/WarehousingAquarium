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
    UserRepository userRepository;
    WarehouseRepository warehouseRepository;
    StatusRepository statusRepository;

    @Autowired
    public ImportServiceImpl(ImportRepository importRepository, ProductBranchRepository productBranchRepository,
                             ProductRepository productRepository, BranchRepository branchRepository,
                             SupplierRepository supplierRepository, PaymentTypeRepository paymentTypeRepository,
                             UserRepository userRepository, WarehouseRepository warehouseRepository, StatusRepository statusRepository) {
        this.importRepository = importRepository;
        this.productBranchRepository = productBranchRepository;
        this.productRepository = productRepository;
        this.branchRepository = branchRepository;
        this.supplierRepository = supplierRepository;
        this.paymentTypeRepository = paymentTypeRepository;
        this.userRepository = userRepository;
        this.warehouseRepository = warehouseRepository;
        this.statusRepository = statusRepository;
    }

    @Override
    public ImportEntity addListImport(ImportRequest importRequest) {
        List<ProductBranchEntity> productBranchEntities = new ArrayList<>();
        ImportEntity importEntity = new ImportEntity();
        importEntity.setImportID(importRequest.getImportID());
        Optional<BranchEntity> branchEntity = branchRepository.findById(importRequest.getBranchId());
        Optional<SupplierEntity> supplierEntity = supplierRepository.findById(importRequest.getSupplierId());
        Optional<PaymentTypeEntity> paymentTypeEntity = paymentTypeRepository.findById(importRequest.getPaymentType());
        int number = 0;
        boolean isUpdate = false;

        for (ProductImportRequest productImportRequest : importRequest.getProducts()) {
            ProductBatchEntity productBatchEntity = new ProductBatchEntity();
            if (productImportRequest.getCanExpired() != null && productImportRequest.getCanExpired()) {
                productBatchEntity.setUpdatedBy(importRequest.getEmployee());
                productBatchEntity.setSupplierId(importRequest.getSupplierId());
                if (productImportRequest.getProductBatchId() != null) {
                    Optional<ProductBatchEntity> warehouseEntity = warehouseRepository.findById(productImportRequest.getProductBatchId());
                    if (warehouseEntity.isPresent()) {
                        productBatchEntity = warehouseEntity.get();
                        Double price = (productBatchEntity.getPrice() + productImportRequest.getUnitPrice()) / (productBatchEntity.getQuantity() + productImportRequest.getSaleQuantity());
                        productBatchEntity.setPrice(price);
                        productBatchEntity.setQuantity(productBatchEntity.getQuantity() + productImportRequest.getSaleQuantity());
                    }
                } else {
                    productBatchEntity.setPrice(productImportRequest.getUnitPrice());
                    if (productImportRequest.getSaleQuantity() == null) {
                        productImportRequest.setSaleQuantity(0L);
                    }
                    productBatchEntity.setQuantity((long) productImportRequest.getSaleQuantity().intValue());
                    productBatchEntity.setCreatedDate(new java.sql.Date(System.currentTimeMillis()));
                    productBatchEntity.setCreatedBy(importRequest.getEmployee());
                    productBatchEntity.setExpiredDate(productImportRequest.getExpireDate());
                }
                productBatchEntity = warehouseRepository.save(productBatchEntity);
            }

            number += productImportRequest.getSaleQuantity();
            ProductBranchEntity productBranchEntity = new ProductBranchEntity();
            Long oldQuantity = 0L;
            Long newQuantity = 0L;
            if (productImportRequest.getProductBranchId() != null) {
                isUpdate = true;
                productBranchEntity.setProBranchID(productImportRequest.getProductBranchId());
                Optional<ProductBranchEntity> optionalProductBranch = productBranchRepository.findById(productImportRequest.getProductBranchId());
                if (optionalProductBranch.isPresent()) {
                    productBranchEntity = optionalProductBranch.get();
                    oldQuantity = productBranchEntity.getSaleQuantity();
                }
            }
            newQuantity = productImportRequest.getSaleQuantity() - oldQuantity;
            Optional<ProductEntity> productEntity = productRepository.findById(productImportRequest.getProductId());

            if (productEntity.isPresent()) {
                ProductEntity entity = productEntity.get();
                if (productImportRequest.getProductBranchId() == null) {
                    double newPrice = (entity.getSaleQuantity() * entity.getUnitPrice() + productImportRequest.getSaleQuantity() * productImportRequest.getUnitPrice()) / (entity.getSaleQuantity() + productImportRequest.getSaleQuantity());
                    newPrice = Math.ceil(newPrice * 100) / 100;
                    entity.setUnitPrice(newPrice);
                }
                entity.setSaleQuantity(entity.getSaleQuantity() + newQuantity);
                productRepository.save(entity);
            }

            ProductBranchEntity finalProductBranchEntity = productBranchEntity;
            productEntity.ifPresent(entity -> finalProductBranchEntity.setProductID(entity.getProductId()));
            productBranchEntity.setSaleQuantity(productImportRequest.getSaleQuantity());
            productBranchEntity.setUnitPrice(importRequest.getImportPrice());
            productBranchEntity.setProductBatchId(productBatchEntity.getProductBatchId());
            branchEntity.ifPresent(entity -> finalProductBranchEntity.setBranchID(entity.getBranchId()));

            productBranchEntities.add(finalProductBranchEntity);
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
        if (!importRequest.getStatusPayment() && !isUpdate) {
            if (supplierEntity.isPresent()) {
                SupplierEntity supplier = supplierEntity.get();
                supplier.setDept(supplier.getDept() + importRequest.getImportPrice());
                supplierRepository.save(supplierEntity.get());
            }
        }
        importEntity.setTaxID(importRequest.getTaxId());
        importEntity.setBranchID(importEntity.getBranchID());
        ImportEntity importId = importRepository.save(importEntity);
        for (ProductBranchEntity entity : productBranchEntities) {
            entity.setImportId(importId.getImportID());
            entity.setCreatedDate(new java.sql.Date(System.currentTimeMillis()));
        }
        productBranchRepository.saveAll(productBranchEntities);
        return importId;

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
        int start = pageIndex * pageSize;
        for (int i = 0; i < importEntities.size(); i++) {
            ImportEntity importEntity = importEntities.get(i);
            if (i >= start && i < start + pageSize) {
                if (i > start + pageSize) {
                    break;
                }
                BranchEntity branchEntity = null;
                SupplierEntity supplierEntity = null;
                AccountEntity account = null;
                List<ProductBranchEntity> productBranchEntities;
                if (importEntity.getBranchID() != null) {
                    Optional<BranchEntity> optionalBranch = branchRepository.findById(importEntity.getBranchID());
                    if (optionalBranch.isPresent()) {
                        branchEntity = optionalBranch.get();
                    }
                }
                if (importEntity.getSupplierID() != null) {
                    Optional<SupplierEntity> optionalSupplierEntity = supplierRepository.findById(importEntity.getSupplierID());
                    if (optionalSupplierEntity.isPresent()) {
                        supplierEntity = optionalSupplierEntity.get();
                    }
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
                    ProductBatchEntity productBatchEntity = new ProductBatchEntity();
                    if (entity.getProductBatchId() != null) {
                        Optional<ProductBatchEntity> optionalProductBatchEntity = warehouseRepository.findById(entity.getProductBatchId());
                        if (optionalProductBatchEntity.isPresent()) {
                            productBatchEntity = optionalProductBatchEntity.get();
                            importProductDTO.setProductBatch(productBatchEntity);
                        }
                    }


                    Optional<ProductEntity> productEntity = productRepository.findById(entity.getProductID());
                    productEntity.ifPresent(product -> importProductDTO.setProductCode(product.getProductCode()));
                    productEntity.ifPresent(product -> importProductDTO.setProductName(product.getProductName()));
                    productEntity.ifPresent(product -> importProductDTO.setSaleQuantity(product.getSaleQuantity()));
                    productEntity.ifPresent(product -> importProductDTO.setImage(product.getImage()));
                    productEntity.ifPresent(product -> importProductDTO.setColor(product.getColor()));
                    productEntity.ifPresent(product -> importProductDTO.setCanExpired(product.getCanExpired()));
                    if (productBatchEntity.getPrice() != null) {
                        importProductDTO.setUnitPrice(Math.ceil(productBatchEntity.getPrice() * 100) / 100);
                    }
                    productEntity.ifPresent(product -> importProductDTO.setUnitName(product.getUnitName()));
                    importProductDTOS.add(importProductDTO);
                }
                ImportDTO importDTO = ImportMapper.mapImportEntityToDTO(importEntity, branchEntity, supplierEntity, account, userRepository);
                importDTO.setProducts(importProductDTOS);
                Optional<StatusEntity> statusOptional = statusRepository.findById(importEntity.getStatus());
                statusOptional.ifPresent(statusEntity -> importDTO.setStatus(statusEntity.getSttId()));
                list.add(importDTO);
            }
        }
        return list;
    }

    @Override
    public ImportDTO getImportById(Long id) {
        Optional<ImportEntity> importEntities = importRepository.findById(id);
        if (!importEntities.isPresent()) {
            return null;
        }
        BranchEntity branchEntity = new BranchEntity();
        SupplierEntity supplierEntity = new SupplierEntity();
        AccountEntity account = new AccountEntity();
        List<ProductBranchEntity> productBranchEntities;
        if (importEntities.get().getBranchID() != null) {
            Optional<BranchEntity> optionalBranch = branchRepository.findById(importEntities.get().getBranchID());
            if (optionalBranch.isPresent()) {
                branchEntity = optionalBranch.get();
            }
        }
        if (importEntities.get().getSupplierID() != null) {
            Optional<SupplierEntity> optionalSupplier = supplierRepository.findById(importEntities.get().getSupplierID());
            if (optionalSupplier.isPresent()) {
                supplierEntity = optionalSupplier.get();
            }
        }
        if (importEntities.get().getUserID() != null) {
            Optional<AccountEntity> optionalAccount = userRepository.findById(importEntities.get().getUserID());
            if (optionalAccount.isPresent()) {
                account = optionalAccount.get();
            }
        }
        productBranchEntities = productBranchRepository.findAllByImportId(importEntities.get().getImportID());
        List<ImportProductDTO> importProductDTOS = new ArrayList<>();
        for (ProductBranchEntity entity : productBranchEntities) {
            ImportProductDTO importProductDTO = new ImportProductDTO();
            ProductBatchEntity productBatchEntity;
            if (entity.getProductBatchId() != null) {
                Optional<ProductBatchEntity> optionalProductBatchEntity = warehouseRepository.findById(entity.getProductBatchId());
                if (optionalProductBatchEntity.isPresent()) {
                    productBatchEntity = optionalProductBatchEntity.get();
                    importProductDTO.setProductBatch(productBatchEntity);
                }
            }
            importProductDTO.setProductId(entity.getProductID());
            Optional<ProductEntity> productEntity = productRepository.findById(entity.getProductID());
            productEntity.ifPresent(product -> importProductDTO.setProductCode(product.getProductCode()));
            productEntity.ifPresent(product -> importProductDTO.setProductName(product.getProductName()));
            productEntity.ifPresent(product -> importProductDTO.setSaleQuantity(entity.getSaleQuantity()));
            productEntity.ifPresent(product -> importProductDTO.setImage(product.getImage()));
            productEntity.ifPresent(product -> importProductDTO.setColor(product.getColor()));
            importProductDTO.setUnitPrice(entity.getUnitPrice());
            productEntity.ifPresent(product -> importProductDTO.setCanExpired(product.getCanExpired()));
            productEntity.ifPresent(product -> importProductDTO.setUnitName(product.getUnitName()));
            productEntity.ifPresent(product -> importProductDTO.setProductBranchId(entity.getProBranchID()));

            importProductDTOS.add(importProductDTO);
        }
        ImportDTO importDTO = ImportMapper.mapImportEntityToDTO(importEntities.get(), branchEntity, supplierEntity, account, userRepository);
        Optional<StatusEntity> statusOptional = statusRepository.findById(importEntities.get().getStatus());


        statusOptional.ifPresent(statusEntity -> importDTO.setStatus(statusEntity.getSttId()));
        importDTO.setProducts(importProductDTOS);
        importDTO.setSttStore(importEntities.get().getSttStore());
        importDTO.setStatusPayment(importEntities.get().getStatusPayment());
        return importDTO;
    }

    @Override
    public List<ImportDTO> getImportBySupplierId(int pageIndex, int pageSize, int id) {
        List<ImportEntity> importEntities;
        if (id == 0) {
            importEntities = importRepository.findAll();
        } else {
            importEntities = importRepository.findAllBySupplierID((long) id);
        }
        List<ImportDTO> list = new ArrayList<>();
        if (importEntities.isEmpty()) {
            return list;
        }
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

                    if (entity.getProductBatchId() != null) {
                        Optional<ProductBatchEntity> optionalProductBatchEntity = warehouseRepository.findById(entity.getProductBatchId());
                        if (optionalProductBatchEntity.isPresent()) {
                            ProductBatchEntity productBatchEntity = optionalProductBatchEntity.get();
                            importProductDTO.setProductBatch(productBatchEntity);
                        }
                    }

                    importProductDTO.setProductId(entity.getProductID());
                    Optional<ProductEntity> productEntity = productRepository.findById(entity.getProductID());
                    productEntity.ifPresent(product -> importProductDTO.setProductCode(product.getProductCode()));
                    productEntity.ifPresent(product -> importProductDTO.setProductName(product.getProductName()));
                    productEntity.ifPresent(product -> importProductDTO.setSaleQuantity(entity.getSaleQuantity()));
                    productEntity.ifPresent(product -> importProductDTO.setImage(product.getImage()));
                    productEntity.ifPresent(product -> importProductDTO.setColor(product.getColor()));
                    productEntity.ifPresent(product -> importProductDTO.setCanExpired(product.getCanExpired()));
                    productEntity.ifPresent(product -> importProductDTO.setUnitName(product.getUnitName()));
                    productEntity.ifPresent(product -> importProductDTO.setUnitPrice(product.getUnitPrice()));
                    productEntity.ifPresent(product -> importProductDTO.setProductBranchId(entity.getProBranchID()));
                    importProductDTOS.add(importProductDTO);
                }
                ImportDTO importDTO = ImportMapper.mapImportEntityToDTO(importEntity, branchEntity, supplierEntity, account, userRepository);
                importDTO.setProducts(importProductDTOS);
                importDTO.setSttStore(importEntity.getSttStore());
                importDTO.setStatusPayment(importEntity.getStatusPayment());
                list.add(importDTO);
            }
        }
        return list;
    }


}
