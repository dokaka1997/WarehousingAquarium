package com.warehousing.aquarium.model.request;

import lombok.Data;

@Data
public class ProductImportRequest {
    private Long productId;
    private Long saleQuantity;
    private Double totalPrice;
}
