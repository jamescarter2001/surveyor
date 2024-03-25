package com.carter.surveyorbackend.data.repo;

import com.carter.surveyorbackend.data.repo.entity.SurveyQuestionResponse;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for abstracting the survey question response database table
 * connection.
 */
public interface SurveyQuestionResponseRepository extends JpaRepository<SurveyQuestionResponse, Long> {
}
