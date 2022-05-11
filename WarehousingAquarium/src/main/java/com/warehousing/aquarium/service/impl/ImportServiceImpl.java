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
    WarehouseRepository warehouseRepository;

    @Autowired
    public ImportServiceImpl(ImportRepository importRepository, ProductBranchRepository productBranchRepository,
                             ProductRepository productRepository, BranchRepository branchRepository,
                             SupplierRepository supplierRepository, PaymentTypeRepository paymentTypeRepository,
                             StatusRepository statusRepository,
                             UserRepository userRepository, WarehouseRepository warehouseRepository) {
        this.importRepository = importRepository;
        this.productBranchRepository = productBranchRepository;
        this.productRepository = productRepository;
        this.branchRepository = branchRepository;
        this.supplierRepository = supplierRepository;
        this.paymentTypeRepository = paymentTypeRepository;
        this.statusRepository = statusRepository;
        this.userRepository = userRepository;
        this.warehouseRepository = warehouseRepository;
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

            if (productImportRequest.getCanExpire()) {
                WarehouseEntity entity = new WarehouseEntity();
                entity.setUpdatedBy(importRequest.getEmployee());
                entity.setSupplierId(importRequest.getSupplierId());
                if (productImportRequest.getWareHouseId() != null) {
                    Optional<WarehouseEntity> warehouseEntity = warehouseRepository.findById(productImportRequest.getWareHouseId());
                    if (warehouseEntity.isPresent()) {
                        entity = warehouseEntity.get();
                        Double price = (entity.getPrice() + productImportRequest.getPrice()) / (entity.getQuantity() + productImportRequest.getSaleQuantity());
                        entity.setPrice(price);
                        entity.setQuantity((int) (entity.getQuantity() + productImportRequest.getSaleQuantity()));
                    }
                } else {
                    entity.setPrice(productImportRequest.getPrice());
                    entity.setQuantity(productImportRequest.getSaleQuantity().intValue());
                    entity.setCreatedDate(new java.sql.Date(System.currentTimeMillis()));
                    entity.setCreatedBy(importRequest.getEmployee());
                    entity.setExpiredDate(productImportRequest.getExpireDate());
                }
                warehouseRepository.save(entity);
            }


            number += productImportRequest.getSaleQuantity();
            ProductBranchEntity productBranchEntity = new ProductBranchEntity();
            Optional<ProductEntity> productEntity = productRepository.findById(productImportRequest.getProductId());
            if (productEntity.isPresent()) {
                ProductEntity entity = productEntity.get();
                double newPrice = (entity.getStockQuantity() * entity.getUnitPrice() + productImportRequest.getSaleQuantity() * productImportRequest.getSaleQuantity()) / (entity.getStockQuantity() + productImportRequest.getSaleQuantity());
                entity.setStockQuantity((int) (entity.getStockQuantity() + productImportRequest.getSaleQuantity()));
                entity.setSaleQuantity((int) (entity.getSaleQuantity() + productImportRequest.getSaleQuantity()));
                entity.setUnitPrice(newPrice);
                productRepository.save(entity);
            }
            productEntity.ifPresent(entity -> productBranchEntity.setProductID(entity.getProductId()));
            productBranchEntity.setQuantityOnHand(productImportRequest.getSaleQuantity());
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
        if (!importRequest.getStatusPayment()) {
            if (supplierEntity.isPresent()) {
                supplierEntity.get().setDept(importRequest.getImportPrice());
                supplierRepository.save(supplierEntity.get());
            }
        }
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
                    Optional<ProductEntity> productEntity = productRepository.findById(entity.getProductID());
                    productEntity.ifPresent(product -> importProductDTO.setProductCode(product.getProductCode()));
                    productEntity.ifPresent(product -> importProductDTO.setProductName(product.getProductName()));
                    productEntity.ifPresent(product -> importProductDTO.setSaleQuantity(product.getSaleQuantity()));
                    productEntity.ifPresent(product -> importProductDTO.setImage(product.getImage()));
                    productEntity.ifPresent(product -> importProductDTO.setColor(product.getColor()));
                    productEntity.ifPresent(product -> importProductDTO.setUnitPrice(product.getUnitPrice()));
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
            importProductDTO.setProductId(entity.getProductID());
            Optional<ProductEntity> productEntity = productRepository.findById(entity.getProductID());
            productEntity.ifPresent(product -> importProductDTO.setProductCode(product.getProductCode()));
            productEntity.ifPresent(product -> importProductDTO.setProductName(product.getProductName()));
            productEntity.ifPresent(product -> importProductDTO.setSaleQuantity(product.getSaleQuantity()));
            productEntity.ifPresent(product -> importProductDTO.setImage(product.getImage()));
            productEntity.ifPresent(product -> importProductDTO.setColor(product.getColor()));
            productEntity.ifPresent(product -> importProductDTO.setUnitPrice(product.getUnitPrice()));
            productEntity.ifPresent(product -> importProductDTO.setUnitName(product.getUnitName()));
            importProductDTOS.add(importProductDTO);
        }
        ImportDTO importDTO = ImportMapper.mapImportEntityToDTO(importEntities.get(), branchEntity, supplierEntity, account, statusEntities);
        importDTO.setListProduct(importProductDTOS);
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
                    productEntity.ifPresent(product -> importProductDTO.setUnitPrice(product.getUnitPrice()));
                    importProductDTOS.add(importProductDTO);
                }
                ImportDTO importDTO = ImportMapper.mapImportEntityToDTO(importEntity, branchEntity, supplierEntity, account, statusEntities);
                importDTO.setListProduct(importProductDTOS);
                list.add(importDTO);
            }
        }
        return list;
    }


}
