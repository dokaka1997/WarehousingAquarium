package com.warehousing.aquarium.controller;

import com.warehousing.aquarium.entity.CommentEntity;
import com.warehousing.aquarium.model.response.CommentDTO;
import com.warehousing.aquarium.model.response.TopicDTO;
import com.warehousing.aquarium.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("comment")
public class CommentController {
    CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentDTO> addNewComment(@RequestBody CommentEntity commentEntity) {
        return ResponseEntity.ok(commentService.addNewComment(commentEntity));
    }

    @GetMapping
    ResponseEntity<List<CommentDTO>> getCommentByTopic(@RequestParam Long topicId, @RequestParam int pageIndex, @RequestParam int pageSize) {
        return ResponseEntity.ok(commentService.getAllComment(pageIndex, pageSize, topicId));
    }
}
