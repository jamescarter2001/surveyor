package com.carter.surveyorbackend.data.repo;

import com.carter.surveyorbackend.data.repo.entity.QuestionType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for abstracting the question type database table connection.
 */
public interface QuestionTypeRepository extends JpaRepository<QuestionType, Long> {
}
