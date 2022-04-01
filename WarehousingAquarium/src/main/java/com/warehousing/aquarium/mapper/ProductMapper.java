package com.warehousing.aquarium.mapper;

import com.warehousing.aquarium.entity.ProductEntity;
import com.warehousing.aquarium.model.response.*;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class ProductMapper {

    public static List<ProductDTO> mapListProductEntityToDTO(List<ProductEntity> productEntities) {
        List<ProductDTO> products = new ArrayList<>();
        ModelMapper mapper = new ModelMapper();
        for (ProductEntity entity : productEntities) {
            ProductDTO dto = new ProductDTO();

            if (entity.getBrandId() != null) {
                BrandDTO brandId = mapper.map(entity.getBrandId(), BrandDTO.class);
                dto.setBrandId(brandId);
            }

            if (entity.getUnitId() != null) {
                UnitDTO unitId = mapper.map(entity.getUnitId(), UnitDTO.class);
                dto.setUnitId(unitId);
            }

            if (entity.getUserId() != null) {
                AccountDTO userId = mapper.map(entity.getUserId(), AccountDTO.class);
                dto.setUserId(userId);
            }

            if (entity.getSupplierId() != null) {
                SupplierDTO supplierId = mapper.map(entity.getSupplierId(), SupplierDTO.class);
                dto.setSupplierId(supplierId);
            }
            if (entity.getCategoryId() != null) {
                CategoryDTO categoryId = mapper.map(entity.getCategoryId(), CategoryDTO.class);
                dto.setCategoryId(categoryId);
            }

            dto.setProductId(entity.getProductId());
            dto.setProductCode(entity.getProductCode());
            dto.setProductName(entity.getProductName());
            dto.setCreatedDate(entity.getCreatedDate());
            dto.setBarCode(entity.getBarCode());
            dto.setUnitPrice(entity.getUnitPrice());
            dto.setRetailPrice(entity.getRetailPrice());
            dto.setWholesalePrice(entity.getWholesalePrice());
            dto.setModifyCreate(entity.getModifyCreate());
            dto.setDescription(entity.getDescription());
            dto.setTag(entity.getTag());
            dto.setSale(entity.isSale());
            dto.setSaleQuantity(entity.getSaleQuantity());
            dto.setStockQuantity(entity.getStockQuantity());
            dto.setImage(entity.getImage());
            dto.setColor(entity.getColor());
            dto.setSttId(entity.getStatus());
            dto.setClassifyId(entity.getClassifyId());

            products.add(dto);
        }
        return products;
    }

    public static ProductDTO mapProductEntityToDTO(ProductEntity entity) {
        ModelMapper mapper = new ModelMapper();

        ProductDTO dto = new ProductDTO();

        if (entity.getBrandId() != null) {
            BrandDTO brandId = mapper.map(entity.getBrandId(), BrandDTO.class);
            dto.setBrandId(brandId);
        }

        if (entity.getUnitId() != null) {
            UnitDTO unitId = mapper.map(entity.getUnitId(), UnitDTO.class);
            dto.setUnitId(unitId);
        }

        if (entity.getUserId() != null) {
            AccountDTO userId = mapper.map(entity.getUserId(), AccountDTO.class);
            dto.setUserId(userId);
        }

        if (entity.getSupplierId() != null) {
            SupplierDTO supplierId = mapper.map(entity.getSupplierId(), SupplierDTO.class);
            dto.setSupplierId(supplierId);
        }
        if (entity.getCategoryId() != null) {
            CategoryDTO categoryId = mapper.map(entity.getCategoryId(), CategoryDTO.class);
            dto.setCategoryId(categoryId);
        }

        dto.setProductId(entity.getProductId());
        dto.setProductCode(entity.getProductCode());
        dto.setProductName(entity.getProductName());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setBarCode(entity.getBarCode());
        dto.setUnitPrice(entity.getUnitPrice());
        dto.setRetailPrice(entity.getRetailPrice());
        dto.setWholesalePrice(entity.getWholesalePrice());
        dto.setModifyCreate(entity.getModifyCreate());
        dto.setDescription(entity.getDescription());
        dto.setTag(entity.getTag());
        dto.setImage(entity.getImage());
        dto.setSale(entity.isSale());
        dto.setSaleQuantity(entity.getSaleQuantity());
        dto.setStockQuantity(entity.getStockQuantity());
        return dto;
    }
}
