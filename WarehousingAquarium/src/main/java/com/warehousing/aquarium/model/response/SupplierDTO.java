package com.warehousing.aquarium.model.response;

import lombok.Data;

@Data
public class SupplierDTO {
    private Long supplierId;
    private String supplierName;
    private int supplierCode;
    private String group;
    private String email;
    private String phone;
    private boolean status;
    private String taxIdentificationNumber;
    private double dept;
    private String address;
    private String user;
    private Long userId;
    private String description;
}
