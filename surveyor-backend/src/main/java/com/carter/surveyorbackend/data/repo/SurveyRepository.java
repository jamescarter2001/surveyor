package com.carter.surveyorbackend.data.repo;

import com.carter.surveyorbackend.data.repo.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for abstracting the survey database table connection.
 */
public interface SurveyRepository extends JpaRepository<Survey, Long> {
}
