package com.carter.surveyorbackend.data.repo;

import com.carter.surveyorbackend.data.repo.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for abstracting the question database table connection.
 */
public interface QuestionRepository extends JpaRepository<Question, Long> {
}
