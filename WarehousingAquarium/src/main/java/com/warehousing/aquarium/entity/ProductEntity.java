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

    private Double unitPrice;

    private String unitName;

    private Double retailPrice;

    private Double wholesalePrice;

    private Date modifyCreate;

    private String description;

    private String tag;

    private Boolean isSale;

    private Long saleQuantity;

    private String image;

    private String color;

    private Long status;

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
