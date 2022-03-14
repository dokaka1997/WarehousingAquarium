package com.warehousing.aquarium.service.impl;

import com.warehousing.aquarium.entity.*;
import com.warehousing.aquarium.exception.MissingFieldException;
import com.warehousing.aquarium.mapper.ProductMapper;
import com.warehousing.aquarium.model.request.ProductRequest;
import com.warehousing.aquarium.model.response.ProductDTO;
import com.warehousing.aquarium.repository.*;
import com.warehousing.aquarium.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    ProductRepository productRepository;
    BrandRepository brandRepository;
    UnitRepository unitRepository;
    UserRepository userRepository;
    SupplierRepository supplierRepository;
    CategoryRepository categoryRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, BrandRepository brandRepository,
                              UnitRepository unitRepository, UserRepository userRepository, SupplierRepository supplierRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.brandRepository = brandRepository;
        this.unitRepository = unitRepository;
        this.userRepository = userRepository;
        this.supplierRepository = supplierRepository;
        this.categoryRepository = categoryRepository;
    }


    @Override
    public List<ProductDTO> getAllProducts(int pageIndex, int pageSize) {
        Page<ProductEntity> productEntities = productRepository.findAll(PageRequest.of(pageIndex, pageSize));
        return ProductMapper.mapListProductEntityToDTO(productEntities.getContent());
    }

    @Override
    public ProductDTO getProductById(Long id) {
        return ProductMapper.mapProductEntityToDTO(productRepository.findById(id).get());
    }

    @Override
    public Boolean createProduct(ProductRequest product) {
        ProductEntity productEntity = new ProductEntity();

        Optional<BrandEntity> optionalBrand = brandRepository.findById(product.getBrandId());

        Optional<UnitEntity> optionalUnit = unitRepository.findById(product.getUnitId());

        Optional<AccountEntity> optionalAccount = userRepository.findById(product.getUserId());

        Optional<SupplierEntity> optionalSupplier = supplierRepository.findById(product.getSupplierId());

        Optional<CategoryEntity> optionalCategory = categoryRepository.findById(product.getCategoryId());

        if (!optionalAccount.isPresent() || !optionalBrand.isPresent()
                || !optionalUnit.isPresent() || !optionalSupplier.isPresent() || !optionalCategory.isPresent()) {
            throw new MissingFieldException("Missing value");
        }
        productEntity.setProductName(product.getProductName());
        productEntity.setCreatedDate(product.getCreatedDate());
        productEntity.setProductCode(product.getProductCode());
        productEntity.setBarCode(product.getBarCode());
        productEntity.setUnitPrice(product.getUnitPrice());
        productEntity.setRetailPrice(product.getRetailPrice());
        productEntity.setWholesalePrice(product.getWholesalePrice());
        productEntity.setModifyCreate(product.getModifyCreate());
        productEntity.setDescription(product.getDescription());
        productEntity.setTag(product.getTag());
        productEntity.setSale(product.isSale());
        productEntity.setSaleQuantity(product.getSaleQuantity());
        productEntity.setStockQuantity(product.getStockQuantity());

        productEntity.setBrandId(optionalBrand.get());
        productEntity.setUnitId(optionalUnit.get());
        productEntity.setUserId(optionalAccount.get());
        productEntity.setSupplierId(optionalSupplier.get());
        productEntity.setCategoryId(optionalCategory.get());

        try {
            productRepository.save(productEntity);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return true;
    }
}
