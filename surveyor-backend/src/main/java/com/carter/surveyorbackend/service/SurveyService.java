package com.carter.surveyorbackend.service;

import com.carter.surveyorbackend.data.repo.entity.Survey;
import com.carter.surveyorbackend.data.repo.entity.SurveyQuestionResponse;
import com.carter.surveyorbackend.data.source.SurveyDataSource;
import com.carter.surveyorbackend.data.source.SurveyQuestionResponseDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Service class for interacting with survey data.
 */
@Service
@RequiredArgsConstructor
public class SurveyService {

    private final SurveyDataSource surveyDataSource;
    private final SurveyQuestionResponseDataSource surveyQuestionResponseDataSource;

    private final Clock clock;

    /**
     * Gets all the surveys stored in the database.
     * @return A List of Survey objects.
     */
    public List<Survey> getAllSurveys() {
        return surveyDataSource.getAll();
    }

    /**
     * Gets the survey with the id that matches the input.
     * @param id A long equal to the id of the requested survey.
     * @return An Optional containing the requested survey.
     */
    public Optional<Survey> getForId(final Long id) {
        return surveyDataSource.getById(id);
    }

    /**
     * Timestamps and saves the provided survey question responses to the database.
     * @param surveyQuestionResponses A List of SurveyQuestionResponse objects to persist to the database.
     * @return A List of SurveyQuestionResponse objects that have been persisted.
     */
    public List<SurveyQuestionResponse> saveSurveyResponses(final List<SurveyQuestionResponse> surveyQuestionResponses) {
        Instant now = Instant.now(clock);
        surveyQuestionResponses.forEach(r -> r.setTimestamp(Timestamp.from(now)));

        return surveyQuestionResponseDataSource.saveResponses(surveyQuestionResponses);
    }
}
