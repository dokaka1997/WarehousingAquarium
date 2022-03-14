package com.warehousing.aquarium.model.response;

import lombok.Data;

import java.util.Date;

@Data
public class CategoryDTO {
    private Long categoryId;
    private String categoryName;
    Date createDate;
}
