package com.warehousing.aquarium.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "branch")
public class BranchEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long branchId;

    private String branchName;

    private String branchPhone;

    private String address;

    private String district;

    private String city;

    private String province;

    private boolean isActive;



}
