package com.warehousing.aquarium.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "export")
public class ExportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long exportID;
    private Date exportTime;
    private Double exportPrice;
    private Long numberExport;
    private Long supplierID;
    private Long paymentID;
    private Long taxID;
    private Long UserID;
    private Long branchID;
    private Long status;
    private Long sttStore;
    private Boolean statusPayment;
}
