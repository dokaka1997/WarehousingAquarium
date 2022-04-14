package com.warehousing.aquarium.service.impl;

import com.warehousing.aquarium.entity.AccountEntity;
import com.warehousing.aquarium.entity.TopicEntity;
import com.warehousing.aquarium.model.response.TopicDTO;
import com.warehousing.aquarium.repository.TopicRepository;
import com.warehousing.aquarium.repository.UserRepository;
import com.warehousing.aquarium.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TopicServiceImpl implements TopicService {
    TopicRepository topicRepository;

    UserRepository userRepository;

    @Autowired
    public TopicServiceImpl(TopicRepository topicRepository, UserRepository userRepository) {
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
    }

    @Override
    public TopicDTO addNewTopic(TopicEntity topicEntity) {
        TopicDTO topicDTO = new TopicDTO();
        topicEntity.setCreatedDate(new Date());
        topicRepository.save(topicEntity);
        topicDTO.setTopicId(topicEntity.getTopicId());
        topicDTO.setCreatedDate(topicEntity.getCreatedDate());
        topicDTO.setTitle(topicEntity.getTitle());
        Optional<AccountEntity> createBy = userRepository.findById(topicEntity.getCreateBy());
        createBy.ifPresent(accountEntity -> topicDTO.setCreateBy(accountEntity.getName()));
        Optional<AccountEntity> assignTo = userRepository.findById(topicEntity.getAssignTo());
        assignTo.ifPresent(accountEntity -> topicDTO.setAssignTo(accountEntity.getName()));
        return topicDTO;
    }

    @Override
    public List<TopicDTO> getAllTopic(int pageIndex, int pageSize) {
        List<TopicDTO> topicDTOS = new ArrayList<>();
        List<TopicEntity> topicEntities = topicRepository.findAll(PageRequest.of(pageIndex, pageSize)).getContent();
        for (TopicEntity topicEntity : topicEntities) {
            TopicDTO topicDTO = new TopicDTO();
            topicDTO.setTopicId(topicEntity.getTopicId());
            topicDTO.setCreatedDate(topicEntity.getCreatedDate());
            topicDTO.setTitle(topicEntity.getTitle());
            Optional<AccountEntity> createBy = userRepository.findById(topicEntity.getCreateBy());
            createBy.ifPresent(accountEntity -> topicDTO.setCreateBy(accountEntity.getName()));
            Optional<AccountEntity> assignTo = userRepository.findById(topicEntity.getAssignTo());
            assignTo.ifPresent(accountEntity -> topicDTO.setAssignTo(accountEntity.getName()));
            topicDTOS.add(topicDTO);

        }
        return topicDTOS;
    }

    @Override
    public TopicDTO getTopicById(Long id) {
        TopicDTO topicDTO = new TopicDTO();
        Optional<TopicEntity> topicEntity = topicRepository.findById(id);
        if (topicEntity.isPresent()) {
            topicDTO.setTopicId(topicEntity.get().getTopicId());
            topicDTO.setCreatedDate(topicEntity.get().getCreatedDate());
            topicDTO.setTitle(topicEntity.get().getTitle());
            Optional<AccountEntity> createBy = userRepository.findById(topicEntity.get().getCreateBy());
            createBy.ifPresent(accountEntity -> topicDTO.setCreateBy(accountEntity.getName()));
            Optional<AccountEntity> assignTo = userRepository.findById(topicEntity.get().getAssignTo());
            assignTo.ifPresent(accountEntity -> topicDTO.setAssignTo(accountEntity.getName()));
        }
        return topicDTO;
    }
}
