package com.carter.surveyorbackend.data.source;

import com.carter.surveyorbackend.data.repo.entity.SurveyQuestionResponse;
import com.carter.surveyorbackend.data.repo.SurveyQuestionResponseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Data source class for handling all survey question response database table interactions.
 */
@Component
@RequiredArgsConstructor
public class SurveyQuestionResponseDataSource {

    private final SurveyQuestionResponseRepository surveyQuestionResponseRepository;

    public List<SurveyQuestionResponse> saveResponses(List<SurveyQuestionResponse> responses) {
        return surveyQuestionResponseRepository.saveAll(responses);
    }
}
