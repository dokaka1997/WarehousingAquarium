package com.warehousing.aquarium.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "productBranch")
public class ProductBranchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long proBranchID;
    private Long importId;
    private Long exportId;
    private Long branchID;
    private Long productID;
    private Long saleQuantity;
    private Long quantityOnHand;
    private Double totalPrice;

}
