package com.warehousing.aquarium.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
@Table(name = "warehouse")
public class WarehouseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long warehouseId;

    private Long productId;

    private Double price;

    private int quantity;

    private Date createdDate;

    private Long createdBy;

    private Long updatedBy;

    private Long supplierId;

    private Date expiredDate;


}
