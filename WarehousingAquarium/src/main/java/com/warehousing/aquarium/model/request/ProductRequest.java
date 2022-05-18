package com.warehousing.aquarium.model.request;

import lombok.Data;

import java.util.Date;

@Data
public class ProductRequest {
    private Long productId;

    private String productName;

    private Date createdDate;

    private String productCode;

    private String barCode;

    private String unitName;

    private Double unitPrice;

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

    private Long brandId;

    private Long unitId;

    private Long userId;

    private Long supplierId;

    private Long categoryId;

    private Boolean canExpired;

}
