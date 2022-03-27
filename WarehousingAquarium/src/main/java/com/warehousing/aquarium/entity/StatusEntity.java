package com.warehousing.aquarium.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "status")
public class StatusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long sttId;

    private String statusName;

}
