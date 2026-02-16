package com.catodev.onechallengeforum.controller;

import com.catodev.onechallengeforum.dto.topic.TopicCreateDto;
import com.catodev.onechallengeforum.dto.topic.TopicResponseDto;
import com.catodev.onechallengeforum.dto.topic.TopicUpdateDto;
import com.catodev.onechallengeforum.service.topic.TopicService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public Page<TopicResponseDto> findAll(@PageableDefault(size = 10, sort = {"creationDate"}) Pageable pageable) {
        return topicService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public TopicResponseDto findById(@PathVariable @Min(1) Long id) {
        return topicService.findById(id);
    }

    @PutMapping("/{id}")
    public TopicResponseDto update(@PathVariable @Min(1) Long id, @Valid @RequestBody TopicUpdateDto dto) {
        return topicService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Min(1) Long id) {
        topicService.delete(id);
    }
}
