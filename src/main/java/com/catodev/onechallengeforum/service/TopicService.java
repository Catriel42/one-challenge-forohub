package com.catodev.onechallengeforum.service;

import com.catodev.onechallengeforum.dto.topic.TopicCreateDto;
import com.catodev.onechallengeforum.dto.topic.TopicResponseDto;
import java.util.List;

public interface TopicService {
    TopicResponseDto create(TopicCreateDto dto);
    List<TopicResponseDto> findAll();
    TopicResponseDto findById(Long id);
}
