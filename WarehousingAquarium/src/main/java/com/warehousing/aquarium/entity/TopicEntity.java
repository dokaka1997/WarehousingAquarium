package com.warehousing.aquarium.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "topic")
public class TopicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long topicId;
    private Long createBy;
    private Date createdDate;
    private Date updatedDate;
    private String title;
    private Long assignTo;
}
