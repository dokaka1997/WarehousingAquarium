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

    private Double unitPrice;

    private String unitName;

    private Double retailPrice;

    private Double wholesalePrice;

    private Date modifyCreate;

    private String description;

    private String tag;

    private Boolean isSale;

    private Long saleQuantity;

    private Long stockQuantity;

    private String image;

    private String color;

    private Long classifyId;

    private Long sttId;

    BrandDTO brandId;

    UnitDTO unitId;

    AccountDTO userId;

    SupplierDTO supplierId;

    CategoryDTO categoryId;

    Boolean canExpired;

}
