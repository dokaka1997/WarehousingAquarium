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

    private double unitPrice;

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

    Long brandId;

    Long unitId;

    Long userId;

    Long supplierId;

    Long categoryId;

    private boolean canExpired;

}
