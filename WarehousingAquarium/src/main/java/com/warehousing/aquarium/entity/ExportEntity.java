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
    private String taxID;
    private Long UserID;
    private Long CustomerID;
    private Long status;
    private Boolean statusPayment;

}
