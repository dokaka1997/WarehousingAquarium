package com.warehousing.aquarium.model.response;

import lombok.Data;

import java.sql.Date;

@Data
public class ProductBatchResponse {

    private Long productBatchId;

    private Long productId;

    private String productName;

    private String productImg;

    private Double price;

    private Long quantity;

    private Date createdDate;

    private String createdBy;

    private String supplier;

    private Long supplierId;

    private Date expiredDate;
}
