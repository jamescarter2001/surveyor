package com.carter.surveyorbackend.data.repo.entity;

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
 * Represents a question stored in the database.
 */
@Entity
@Data
@Table(name = "ea_question")
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;
    private String body;

    /**
     * Represents an associated question type.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "question_type_id")
    private QuestionType type;
}
