package com.warehousing.aquarium.service.impl;

import com.warehousing.aquarium.entity.AccountEntity;
import com.warehousing.aquarium.entity.ProductEntity;
import com.warehousing.aquarium.entity.SupplierEntity;
import com.warehousing.aquarium.entity.ProductBatchEntity;
import com.warehousing.aquarium.model.response.ProductBatchResponse;
import com.warehousing.aquarium.repository.ProductRepository;
import com.warehousing.aquarium.repository.SupplierRepository;
import com.warehousing.aquarium.repository.UserRepository;
import com.warehousing.aquarium.repository.WarehouseRepository;
import com.warehousing.aquarium.service.ProductBatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WarehouseServiceImpl implements ProductBatchService {

    WarehouseRepository warehouseRepository;

    UserRepository userRepository;

    SupplierRepository supplierRepository;

    ProductRepository productRepository;

    @Autowired
    public WarehouseServiceImpl(WarehouseRepository warehouseRepository, UserRepository userRepository,
                                SupplierRepository supplierRepository, ProductRepository productRepository) {
        this.warehouseRepository = warehouseRepository;
        this.userRepository = userRepository;
        this.supplierRepository = supplierRepository;
        this.productRepository = productRepository;
    }

    @Override
    public ProductBatchEntity addNewProductBatch(ProductBatchEntity warehouseEntity) {
        if (warehouseEntity.getProductBatchId() != null) {
            warehouseEntity.setCreatedDate(new java.sql.Date(System.currentTimeMillis()));
        } else {
            warehouseEntity.setUpdatedDate(new java.sql.Date(System.currentTimeMillis()));
        }
        return warehouseRepository.save(warehouseEntity);
    }

    @Override
    public List<ProductBatchResponse> getAllProductBatchById(Long productId) {
        List<ProductBatchEntity> warehouseList = warehouseRepository.findAllByProductId(productId);

        List<ProductBatchResponse> responseList = new ArrayList<>();
        for (ProductBatchEntity warehouseEntity : warehouseList) {
            ProductBatchResponse warehouseResponse = new ProductBatchResponse();
            Optional<ProductEntity> optionalProduct = productRepository.findById(warehouseEntity.getProductId());
            if (optionalProduct.isPresent()) {
                ProductEntity product = optionalProduct.get();
                warehouseResponse.setProductId(product.getProductId());
                warehouseResponse.setProductName(product.getProductName());
                warehouseResponse.setProductImg(product.getImage());
            }
            warehouseResponse.setPrice(warehouseEntity.getPrice());
            warehouseResponse.setQuantity(warehouseEntity.getQuantity());
            warehouseResponse.setCreatedDate(warehouseEntity.getCreatedDate());
            Optional<AccountEntity> optionalAccount = userRepository.findById(warehouseEntity.getCreatedBy());
            if (optionalAccount.isPresent()) {
                AccountEntity account = optionalAccount.get();
                warehouseResponse.setCreatedBy(account.getName());
            }

            Optional<SupplierEntity> optionalSupplier = supplierRepository.findById(warehouseEntity.getSupplierId());
            if (optionalSupplier.isPresent()) {
                SupplierEntity supplierEntity = optionalSupplier.get();
                warehouseResponse.setSupplier(supplierEntity.getSupplierName());
                warehouseResponse.setSupplierId(supplierEntity.getSupplierId());
            }
            warehouseResponse.setProductBatchId(warehouseEntity.getProductBatchId());
            warehouseResponse.setExpiredDate(warehouseEntity.getExpiredDate());
            responseList.add(warehouseResponse);
        }

        return responseList;
    }

    @Override
    public List<ProductBatchResponse> getAllProductBatch() {
        List<ProductBatchEntity> warehouseList = warehouseRepository.findAll();

        List<ProductBatchResponse> responseList = new ArrayList<>();
        for (ProductBatchEntity warehouseEntity : warehouseList) {
            ProductBatchResponse warehouseResponse = new ProductBatchResponse();
            Optional<ProductEntity> optionalProduct = productRepository.findById(warehouseEntity.getProductId());
            if (optionalProduct.isPresent()) {
                ProductEntity product = optionalProduct.get();
                warehouseResponse.setProductId(product.getProductId());
                warehouseResponse.setProductName(product.getProductName());
                warehouseResponse.setProductImg(product.getImage());
            }
            warehouseResponse.setPrice(warehouseEntity.getPrice());
            warehouseResponse.setQuantity(warehouseEntity.getQuantity());
            warehouseResponse.setCreatedDate(warehouseEntity.getCreatedDate());
            Optional<AccountEntity> optionalAccount = userRepository.findById(warehouseEntity.getCreatedBy());
            if (optionalAccount.isPresent()) {
                AccountEntity account = optionalAccount.get();
                warehouseResponse.setCreatedBy(account.getName());
            }

            Optional<SupplierEntity> optionalSupplier = supplierRepository.findById(warehouseEntity.getSupplierId());
            if (optionalSupplier.isPresent()) {
                SupplierEntity supplierEntity = optionalSupplier.get();
                warehouseResponse.setSupplier(supplierEntity.getSupplierName());
                warehouseResponse.setSupplierId(supplierEntity.getSupplierId());
            }
            warehouseResponse.setProductBatchId(warehouseEntity.getProductBatchId());
            warehouseResponse.setExpiredDate(warehouseEntity.getExpiredDate());
            responseList.add(warehouseResponse);
        }

        return responseList;
    }
}
























