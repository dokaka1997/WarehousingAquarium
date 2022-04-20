package com.warehousing.aquarium.service.impl;

import com.warehousing.aquarium.entity.AccountEntity;
import com.warehousing.aquarium.entity.CommentEntity;
import com.warehousing.aquarium.model.response.CommentDTO;
import com.warehousing.aquarium.model.response.ListCommentResponse;
import com.warehousing.aquarium.repository.CommentRepository;
import com.warehousing.aquarium.repository.UserRepository;
import com.warehousing.aquarium.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CommentServiceImpl implements CommentService {
    CommentRepository commentRepository;

    UserRepository userRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }


    @Override
    public CommentDTO addNewComment(CommentEntity commentEntity) {
        commentEntity.setCreatedDate(new Date());
        commentRepository.save(commentEntity);
        ModelMapper mapper = new ModelMapper();
        return mapper.map(commentEntity, CommentDTO.class);
    }

    @Override
    public ListCommentResponse getAllComment(int pageIndex, int pageSize, Long topicId) {
        ListCommentResponse listCommentResponse = new ListCommentResponse();
        Pageable firstPageWithTwoElements = PageRequest.of(pageIndex, pageSize);
        List<CommentEntity> commentEntities = commentRepository.findAllByTopicId(topicId, firstPageWithTwoElements);
        List<CommentDTO> list = new ArrayList<>();
        for (CommentEntity commentEntity : commentEntities) {
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setCommentId(commentEntity.getCommentId());
            commentDTO.setContent(commentEntity.getContent());
            Optional<AccountEntity> createBy = userRepository.findById(commentEntity.getCreateBy());
            createBy.ifPresent(accountEntity -> commentDTO.setCreateBy(accountEntity.getName()));
            commentDTO.setFile(commentEntity.getFile());
            commentDTO.setCreatedDate(commentEntity.getCreatedDate());
            commentDTO.setContent(commentEntity.getContent());
            list.add(commentDTO);
        }
        Collections.sort(list);
        listCommentResponse.setComments(list);
        listCommentResponse.setTotal(commentRepository.findAllByTopicId(topicId).size());
        return listCommentResponse;
    }
}
