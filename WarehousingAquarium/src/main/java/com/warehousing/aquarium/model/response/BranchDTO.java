package com.warehousing.aquarium.model.response;

import lombok.Data;

@Data
public class BranchDTO {
    private Long branchId;

    private String branchName;

    private String branchPhone;

    private String address;

    private String district;

    private String city;

    private String province;

    private boolean isActive;
}
