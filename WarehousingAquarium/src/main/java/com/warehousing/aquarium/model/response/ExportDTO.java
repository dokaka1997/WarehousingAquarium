package com.warehousing.aquarium.model.response;


import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ExportDTO {
    private Long exportID;
    private Date exportTime;
    private Double exportPrice;
    private String branch;
    private String supplier;
    private Long supplierId;
    private String User;
    private String status;
    private Boolean sttStore;
    private Boolean statusPayment;
    List<ImportProductDTO> listProduct;
}
