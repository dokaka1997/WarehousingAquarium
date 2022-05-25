package com.warehousing.aquarium.model.response;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ImportDTO {
    private Long importID;
    private Date importTime;
    private Double importPrice;
    private String branch;
    private SupplierDTO supplierId;
    private String User;
    private Long status;
    private Boolean sttStore;
    private Boolean statusPayment;
    List<ImportProductDTO> products;
}
