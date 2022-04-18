package com.warehousing.aquarium.controller;

import com.warehousing.aquarium.entity.TopicEntity;
import com.warehousing.aquarium.model.response.ListTopicResponse;
import com.warehousing.aquarium.model.response.TopicDTO;
import com.warehousing.aquarium.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("topic")
public class TopicController {

    TopicService topicService;

    @Autowired
    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @PostMapping
    ResponseEntity<TopicDTO> addNewTopic(@RequestBody TopicEntity topicEntity) {
        return ResponseEntity.ok(topicService.addNewTopic(topicEntity));
    }

    @GetMapping
    ResponseEntity<ListTopicResponse> getAllTopic(@RequestParam int pageIndex, @RequestParam int pageSize) {
        return ResponseEntity.ok(topicService.getAllTopic(pageIndex, pageSize));
    }

    @GetMapping("/{id}")
    ResponseEntity<TopicDTO> getTopicById(@PathVariable Long id) {
        return ResponseEntity.ok(topicService.getTopicById(id));
    }


    @DeleteMapping("/{id}")
    ResponseEntity<Boolean> deleteTopic(@PathVariable Long id) {
        return ResponseEntity.ok(topicService.deleteTopicById(id));
    }
}
