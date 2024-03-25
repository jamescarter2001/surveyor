package com.carter.surveyorbackend.data.repo;

import com.carter.surveyorbackend.data.repo.entity.SurveyQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for abstracting the survey question database table connection.
 */
public interface SurveyQuestionRepository extends JpaRepository<SurveyQuestion, Long> {
}
