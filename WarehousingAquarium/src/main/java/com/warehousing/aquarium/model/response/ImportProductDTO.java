package com.warehousing.aquarium.model.response;

import lombok.Data;


@Data
public class ImportProductDTO {
    private Long productId;

    private String productName;

    private String productCode;

    private int saleQuantity;

    private String image;

    private String color;

    private Long quantity;

    private double price;
}
