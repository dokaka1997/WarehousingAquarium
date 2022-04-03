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
    private String supplier;
    private String User;
    private String status;
    private String sttStore;
    private String statusPayment;
    List<ImportProductDTO> listProduct;
}
