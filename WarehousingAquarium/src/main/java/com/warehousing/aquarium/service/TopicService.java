package com.warehousing.aquarium.service;

import com.warehousing.aquarium.entity.TopicEntity;
import com.warehousing.aquarium.model.response.TopicDTO;

import java.util.List;

public interface TopicService {
    TopicDTO addNewTopic(TopicEntity topicEntity);

    List<TopicDTO> getAllTopic(int pageIndex, int pageSize);

    TopicDTO getTopicById(Long id);

    Boolean deleteTopicById(Long id);
}
