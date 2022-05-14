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

    private String barCode;

    private double unitPrice;

    private String unitName;

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

    private int status;

    private Boolean canExpired;

    private Double priceImport;

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

    @ManyToOne()
    @JoinColumn(name = "categoryId")
    CategoryEntity categoryId;
}
