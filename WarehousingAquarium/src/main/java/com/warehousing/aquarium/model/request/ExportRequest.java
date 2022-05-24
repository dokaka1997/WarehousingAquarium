package com.warehousing.aquarium.model.request;

import lombok.Data;

import java.util.List;

@Data
public class ExportRequest {
    private Long exportID;
    private String taxId;
    private Long paymentType;
    private Double exportPrice;
    private Double amountPaid;
    private Boolean statusPayment;
    private Long employee;
    private Long customer;
    private Long status;
    private Double priceImport;
    List<ProductImportRequest> products;
}
