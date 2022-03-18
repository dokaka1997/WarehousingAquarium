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
            productEntities = productRepository.findAllByBarCodeOrProductCodeOrProductName(search, search, search);
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

        Optional<BrandEntity> optionalBrand = brandRepository.findById(product.getBrandId());

        Optional<UnitEntity> optionalUnit = unitRepository.findById(product.getUnitId());

        Optional<AccountEntity> optionalAccount = userRepository.findById(product.getUserId());

        Optional<SupplierEntity> optionalSupplier = supplierRepository.findById(product.getSupplierId());

        Optional<CategoryEntity> optionalCategory = categoryRepository.findById(product.getCategoryId());

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
        productEntity.setSttId(product.getSttId());
        productEntity.setClassifyId(product.getClassifyId());

        optionalBrand.ifPresent(productEntity::setBrandId);

        optionalUnit.ifPresent(productEntity::setUnitId);

        optionalAccount.ifPresent(productEntity::setUserId);

        optionalSupplier.ifPresent(productEntity::setSupplierId);

        optionalCategory.ifPresent(productEntity::setCategoryId);

        try {
            productRepository.save(productEntity);
        } catch (Exception exception) {
            exception.printStackTrace();
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
