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
    private int taxIdentificationNumber;
    private double dept;
    private String address;
    private String user;
    private String description;
}
