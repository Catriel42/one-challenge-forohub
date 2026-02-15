package com.catodev.onechallengeforum.repository;

import com.catodev.onechallengeforum.model.Topic;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    boolean existsByTitleAndCourseId(@NotBlank(message = "Title is required") String title, @NotNull(message = "Course ID is required") Long aLong);
}
