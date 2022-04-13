package com.warehousing.aquarium.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "comment")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long commentId;
    private Long topicId;
    private String content;
    private Date createdDate;
    private Long createBy;
    private String file;
}
