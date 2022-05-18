package com.warehousing.aquarium.model.response;


import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ExportDTO {
    private Long exportID;
    private Date exportTime;
    private Double exportPrice;
    private String User;
    private Long CustomerID;
    private String CustomerName;
    private String status;
    private Boolean sttStore;
    private Boolean statusPayment;
    List<ImportProductDTO> listProduct;
}
