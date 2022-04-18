package com.warehousing.aquarium.service;

import com.warehousing.aquarium.entity.TopicEntity;
import com.warehousing.aquarium.model.response.ListTopicResponse;
import com.warehousing.aquarium.model.response.TopicDTO;

public interface TopicService {
    TopicDTO addNewTopic(TopicEntity topicEntity);

    ListTopicResponse getAllTopic(int pageIndex, int pageSize);

    TopicDTO getTopicById(Long id);

    Boolean deleteTopicById(Long id);
}
