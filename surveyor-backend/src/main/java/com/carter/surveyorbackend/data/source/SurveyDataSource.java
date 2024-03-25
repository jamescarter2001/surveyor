package com.carter.surveyorbackend.data.source;

import com.carter.surveyorbackend.data.repo.entity.Survey;
import com.carter.surveyorbackend.data.repo.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Data source class for handling all survey database table interactions.
 */
@Component
@RequiredArgsConstructor
public class SurveyDataSource {

    private final SurveyRepository surveyRepository;

    /**
     * Gets all the surveys stored in the database.
     * @return A Collection of Survey objects containing all the survey data.
     */
    public List<Survey> getAll() {
        return surveyRepository.findAll();
    }

    /**
     * Gets the airport with then id that matches the input.
     * @param id A Long equal to the id for the requested survey
     * @return An Optional containing the Survey object, which may be empty.
     */
    public Optional<Survey> getById(final Long id) {
        return surveyRepository.findById(id);
    }

}
