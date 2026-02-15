package com.catodev.onechallengeforum.controller;

import com.catodev.onechallengeforum.dto.topic.TopicCreateDto;
import com.catodev.onechallengeforum.dto.topic.TopicResponseDto;
import com.catodev.onechallengeforum.service.TopicService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topics")
@AllArgsConstructor
public class TopicController {
    private final TopicService topicService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TopicResponseDto create(@Valid @RequestBody TopicCreateDto dto) {
        return topicService.create(dto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TopicResponseDto> findAll() {
        return topicService.findAll();
    }

    @GetMapping("/{id}")
    public TopicResponseDto findById(@PathVariable Long id) {
        return topicService.findById(id);
    }


}
