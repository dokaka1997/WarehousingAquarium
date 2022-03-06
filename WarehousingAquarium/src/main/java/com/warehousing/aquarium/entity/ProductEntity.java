package com.warehousing.aquarium.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;

    private String productName;

    private Date createdDate;

    private String productCode;

    private int barCode;

    private double unitPrice;

    private double retailPrice;

    private double wholesalePrice;

    private Date modifyCreate;

    private String description;

    private String tag;

    private boolean isSale;

    private int saleQuantity;

    private int stockQuantity;

    @ManyToOne()
    @JoinColumn(name = "brandId")
    BrandEntity brandId;

    @ManyToOne()
    @JoinColumn(name = "unitId")
    UnitEntity unitId;

    @ManyToOne()
    @JoinColumn(name = "userId")
    AccountEntity userId;

    @ManyToOne()
    @JoinColumn(name = "supplierId")
    SupplierEntity supplierId;
}
