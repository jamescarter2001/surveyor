package com.carter.surveyorbackend.data.repo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a question type option stored in the database.
 */
@Entity
@Data
@Table(name = "ea_question_type_option")
@AllArgsConstructor
@NoArgsConstructor
public class QuestionTypeOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionTypeOptionId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "question_type_id")
    @JsonBackReference
    private QuestionType questionType;

    private Integer value;
    private String alias;
}
