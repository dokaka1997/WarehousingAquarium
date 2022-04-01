package com.warehousing.aquarium.service.impl;

import com.warehousing.aquarium.entity.*;
import com.warehousing.aquarium.mapper.ProductMapper;
import com.warehousing.aquarium.model.request.ProductRequest;
import com.warehousing.aquarium.model.response.ProductDTO;
import com.warehousing.aquarium.repository.*;
import com.warehousing.aquarium.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public List<ProductDTO> getAllProducts(int pageIndex, int pageSize, String search) {
        List<ProductEntity> productEntities;
        if (search.isEmpty()) {
            productEntities = productRepository.findAll(PageRequest.of(pageIndex, pageSize)).getContent();
        } else {
            productEntities = productRepository.findAllByBarCodeContainingOrProductCodeContainingOrProductNameContaining(search, search, search);
            if (productEntities.size() <= pageSize) {
                return ProductMapper.mapListProductEntityToDTO(productEntities);
            } else {
                List<ProductEntity> rs = new ArrayList<>();
                int start = pageIndex * pageSize;
                for (int i = 0; i < productEntities.size(); i++) {
                    if (i >= start && i < start + pageSize) {
                        if (i > start + pageSize) {
                            break;
                        }
                        rs.add(productEntities.get(i));
                    }
                }
                return ProductMapper.mapListProductEntityToDTO(rs);
            }
        }

        return ProductMapper.mapListProductEntityToDTO(productEntities);
    }

    @Override
    public ProductDTO getProductById(Long id) {
        return ProductMapper.mapProductEntityToDTO(productRepository.findById(id).get());
    }

    @Override
    public Boolean createProduct(ProductRequest product) {
        ProductEntity productEntity = new ProductEntity();
        if (product.getBrandId() != null) {
            Optional<BrandEntity> optionalBrand = brandRepository.findById(product.getBrandId());
            optionalBrand.ifPresent(productEntity::setBrandId);
        }
        if (product.getUnitId() != null) {
            Optional<UnitEntity> optionalUnit = unitRepository.findById(product.getUnitId());
            optionalUnit.ifPresent(productEntity::setUnitId);
        }

        if (product.getUserId() != null) {
            Optional<AccountEntity> optionalAccount = userRepository.findById(product.getUserId());
            optionalAccount.ifPresent(productEntity::setUserId);
        }

        if (product.getSupplierId() != null) {
            Optional<SupplierEntity> optionalSupplier = supplierRepository.findById(product.getSupplierId());
            optionalSupplier.ifPresent(productEntity::setSupplierId);
        }
        if (product.getCategoryId() != null) {
            Optional<CategoryEntity> optionalCategory = categoryRepository.findById(product.getCategoryId());
            optionalCategory.ifPresent(productEntity::setCategoryId);
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
        productEntity.setColor(product.getColor());
        productEntity.setImage(product.getImage());
        productEntity.setStatus(product.getSttId());
        productEntity.setClassifyId(product.getClassifyId());
        if (product.getProductId() != null) {
            productEntity.setProductId(product.getProductId());
        }
        try {
            productRepository.save(productEntity);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return true;
    }

    @Override
    public Boolean updateListProduct(List<ProductRequest> productRequests) {
        List<ProductEntity> list = new ArrayList<>();
        for (ProductRequest product : productRequests) {
            ProductEntity productEntity = new ProductEntity();
            if (product.getBrandId() != null) {
                Optional<BrandEntity> optionalBrand = brandRepository.findById(product.getBrandId());
                optionalBrand.ifPresent(productEntity::setBrandId);
            }
            if (product.getUnitId() != null) {
                Optional<UnitEntity> optionalUnit = unitRepository.findById(product.getUnitId());
                optionalUnit.ifPresent(productEntity::setUnitId);
            }

            if (product.getUserId() != null) {
                Optional<AccountEntity> optionalAccount = userRepository.findById(product.getUserId());
                optionalAccount.ifPresent(productEntity::setUserId);
            }

            if (product.getSupplierId() != null) {
                Optional<SupplierEntity> optionalSupplier = supplierRepository.findById(product.getSupplierId());
                optionalSupplier.ifPresent(productEntity::setSupplierId);
            }
            if (product.getCategoryId() != null) {
                Optional<CategoryEntity> optionalCategory = categoryRepository.findById(product.getCategoryId());
                optionalCategory.ifPresent(productEntity::setCategoryId);
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
            productEntity.setColor(product.getColor());
            productEntity.setImage(product.getImage());
            productEntity.setStatus(product.getSttId());
            productEntity.setClassifyId(product.getClassifyId());
            if (product.getProductId() != null) {
                productEntity.setProductId(product.getProductId());
            }
            list.add(productEntity);
            try {
                productRepository.save(productEntity);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        return true;
    }

    @Override
    public Boolean deleteProductById(Long id) {
        try {
            productRepository.deleteById(id);
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }
}
