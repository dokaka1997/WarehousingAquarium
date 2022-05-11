package com.warehousing.aquarium.model.response;

import lombok.Data;

import java.sql.Date;

@Data
public class WarehouseResponse {

    private Long warehouseId;

    private Long productId;

    private String productName;

    private String productImg;

    private Double price;

    private int quantity;

    private Date createdDate;

    private String createdBy;

    private String supplier;

    private Date expiredDate;
}
