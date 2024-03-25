package com.carter.surveyorbackend.model.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Represents a response to a survey question sent via a POST request.
 */
@Data
@Accessors(fluent = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SurveyQuestionResponseDTO {
    private String surveyId;
    private String questionId;
    private String facilityId;
    private String response;
}
