package com.warehousing.aquarium.model.response;

import lombok.Data;


@Data
public class ImportProductDTO {
    private Long productId;

    private Long productBranchId;

    private String productName;

    private String productCode;

    private Long saleQuantity;

    private String image;

    private String color;

    private Double unitPrice;

    private String unitName;

    private Boolean canExpired;
}
