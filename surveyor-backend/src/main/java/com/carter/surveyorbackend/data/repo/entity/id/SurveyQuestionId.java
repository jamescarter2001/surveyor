package com.carter.surveyorbackend.data.repo.entity.id;

import com.carter.surveyorbackend.data.repo.entity.Question;
import com.carter.surveyorbackend.data.repo.entity.Survey;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Represents the Composite Key used in the ea_survey_question database table.
 */
@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class SurveyQuestionId implements Serializable {
    private Survey survey;
    private Question question;
}
