package com.warehousing.aquarium.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "supplier")
public class SupplierEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long supplierId;
    private String supplierName;
    private int supplierCode;
    @Column(name = "`group`")
    private String group;
    private String email;
    private String phone;
    private boolean status;
    private String taxIdentificationNumber;

    @OneToMany(mappedBy = "supplierId", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<ProductEntity> products;
    private double dept;
    private String address;
    private Long userId;
    private String description;

}
