package com.warehousing.aquarium.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "customer")
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long customerId;
    private String customerName;
    private String customerAddress;
    private String customerPhone;
    private String customerEmail;
    private String shipAddress;
    private Double debt;
    private String gender;
    private String taxIdentificationNumber;

}
