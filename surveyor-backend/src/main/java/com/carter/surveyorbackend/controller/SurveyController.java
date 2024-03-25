package com.carter.surveyorbackend.controller;

import com.carter.surveyorbackend.data.repo.entity.Survey;
import com.carter.surveyorbackend.data.repo.entity.SurveyQuestionResponse;
import com.carter.surveyorbackend.model.dto.SurveyQuestionResponseDTO;
import com.carter.surveyorbackend.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "surveys")
@RequiredArgsConstructor
public class SurveyController {

    private final SurveyService surveyService;

    /**
     * GET endpoint for retrieving all surveys stored in the database.
     * @return A ResponseEntity containing all the available survey data.
     */
    @GetMapping
    public ResponseEntity<List<Survey>> getAllSurveys() {
        return ResponseEntity.ok(surveyService.getAllSurveys());
    }

    /**
     * GET endpoint for retrieving a single survey stored in the database.
     * @param id A long equal to the id of the requested survey.
     * @return A ResponseEntity containing a Survey object for the given id, if it exists.
     */
    @GetMapping("{id}")
    public ResponseEntity<Survey> getSurvey(@PathVariable Long id) {
        return surveyService.getForId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * POST endpoint for submitting survey responses to the database.
     * @param responses A list of SurveyQuestionResponseDTO objects containing responses to
     *                  various questions.
     * @return A list containing the persisted response data.
     */
    @PostMapping("response")
    public ResponseEntity<List<SurveyQuestionResponse>> submitSurvey(@RequestBody List<SurveyQuestionResponseDTO> responses) {
        List<SurveyQuestionResponse> responseList = responses.stream()
                .map(this::toSurveyQuestionResponse)
                .toList();

        List<SurveyQuestionResponse> result = surveyService.saveSurveyResponses(responseList);

        return ResponseEntity.ok(result);
    }

    /**
     * Helper function for converting a SurveyQuestionResponseDTO object to a native
     * SurveyQuestionResponse object.
     * @param response A SurveyQuestionResponseDTO object to be converted.
     * @return A SurveyQuestionResponse object.
     */
    private SurveyQuestionResponse toSurveyQuestionResponse(SurveyQuestionResponseDTO response) {
        return SurveyQuestionResponse.builder()
                .surveyId(Long.valueOf(response.surveyId()))
                .questionId(Long.valueOf(response.questionId()))
                .facilityId(Long.valueOf(response.facilityId()))
                .response(response.response())
                .build();
    }
}
