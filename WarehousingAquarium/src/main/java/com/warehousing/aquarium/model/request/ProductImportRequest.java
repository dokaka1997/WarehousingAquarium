package com.warehousing.aquarium.model.request;

import lombok.Data;

import java.sql.Date;

@Data
public class ProductImportRequest {
    private Long productId;
    private Long productBranchId;
    private Long saleQuantity;
    private Double unitPrice;
    private Boolean canExpired;
    private Date expireDate;
    private Long productBatchId;
}
