package com.warehousing.aquarium.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "import")
public class ImportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long importID;
    private Date importTime;
    private Double importPrice;
    private Long numberImport;
    private Long supplierID;
    private Long paymentID;
    private Long taxID;
    private Long UserID;
    private Long proBranchID;
    private Long status;
    private Long sttStore;
    private Long statusPayment;

}
