package com.warehousing.aquarium.model.response;


import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ExportDTO implements Comparable<ExportDTO> {
    private Long exportID;
    private Date exportTime;
    private Double exportPrice;
    private String User;
    private Long CustomerID;
    private String CustomerName;
    private Long status;
    private Boolean statusPayment;
    List<ImportProductDTO> products;

    @Override
    public int compareTo(ExportDTO o) {
        return this.getExportTime().compareTo(o.getExportTime());
    }
}
