package com.warehousing.aquarium.model.response;

import lombok.Data;

import java.util.Date;

@Data
public class ProductDTO {

    private Long productId;

    private String productName;

    private Date createdDate;

    private String productCode;

    private String barCode;

    private double unitPrice;

    private String unitName;

    private double retailPrice;

    private double wholesalePrice;

    private Date modifyCreate;

    private String description;

    private String tag;

    private boolean isSale;

    private int saleQuantity;

    private int stockQuantity;

    private String image;

    private String color;

    private int classifyId;

    private int sttId;

    BrandDTO brandId;

    UnitDTO unitId;

    AccountDTO userId;

    SupplierDTO supplierId;

    CategoryDTO categoryId;

}
