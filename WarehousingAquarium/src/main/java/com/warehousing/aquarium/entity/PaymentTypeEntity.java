package com.warehousing.aquarium.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "paymentType")
public class PaymentTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long paymentID;
    private String paymentName;
    private int sttID;
}
