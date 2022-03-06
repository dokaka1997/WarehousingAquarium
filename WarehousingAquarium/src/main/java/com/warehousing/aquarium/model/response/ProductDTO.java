package com.warehousing.aquarium.model.response;

import com.warehousing.aquarium.entity.AccountEntity;
import com.warehousing.aquarium.entity.BrandEntity;
import com.warehousing.aquarium.entity.SupplierEntity;
import com.warehousing.aquarium.entity.UnitEntity;
import lombok.Data;

import java.util.Date;

@Data
public class ProductDTO {

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

    BrandDTO brandId;

    UnitDTO unitId;

    AccountDTO userId;

    SupplierDTO supplierId;

}
