package com.warehousing.aquarium.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
@Table(name = "product_batch")
public class ProductBatchEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productBatchId;

    private Long productId;

    private Double price;

    private Long quantity;

    private Date createdDate;

    private Date updatedDate;

    private Date manufactureDate;

    private Long createdBy;

    private Long updatedBy;

    private Long supplierId;

    private Date expiredDate;


}
