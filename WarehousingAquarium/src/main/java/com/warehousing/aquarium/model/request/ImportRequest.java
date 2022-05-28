package com.warehousing.aquarium.model.request;

import lombok.Data;

import java.util.List;


@Data
public class ImportRequest {
    private Long importID;
    private Long supplierId;
    private Long branchId;
    private Long taxId;
    private Long paymentType;
    private Double importPrice;
    private Long statusImport;
    private Boolean statusPayment;
    private Boolean statusStore;
    private Long employee;
    private Double priceImport;
    List<ProductImportRequest> products;
}
