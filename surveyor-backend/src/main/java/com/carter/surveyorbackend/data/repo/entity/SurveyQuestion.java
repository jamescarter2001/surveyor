package com.carter.surveyorbackend.data.repo.entity;

import com.carter.surveyorbackend.data.repo.entity.id.SurveyQuestionId;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a survey question association stored in the database.
 */
@Entity
@Table(name = "ea_survey_question")
@IdClass(SurveyQuestionId.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SurveyQuestion {
    @Id
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "survey_id")
    @JsonBackReference
    private Survey survey;

    @Id
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "question_id")
    @JsonManagedReference
    private Question question;

    private Integer priority;
}
