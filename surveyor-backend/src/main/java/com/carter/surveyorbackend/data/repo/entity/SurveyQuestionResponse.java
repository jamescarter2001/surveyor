package com.carter.surveyorbackend.data.repo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * Represents a survey question response stored in the database.
 */
@Entity
@Data
@Table(name = "ea_survey_question_response")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class SurveyQuestionResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long surveyQuestionResponseId;
    private Long surveyId;
    private Long questionId;
    private Long facilityId;
    private Timestamp timestamp;
    private String response;
}
