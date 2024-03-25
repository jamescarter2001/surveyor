package com.carter.surveyorbackend.service;

import com.carter.surveyorbackend.data.repo.entity.Question;
import com.carter.surveyorbackend.data.repo.entity.QuestionType;
import com.carter.surveyorbackend.data.repo.entity.QuestionTypeOption;
import com.carter.surveyorbackend.data.repo.entity.Survey;
import com.carter.surveyorbackend.data.repo.entity.SurveyQuestion;
import com.carter.surveyorbackend.data.repo.entity.SurveyQuestionResponse;
import com.carter.surveyorbackend.data.source.SurveyDataSource;
import com.carter.surveyorbackend.data.source.SurveyQuestionResponseDataSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SurveyServiceTest {

    @Mock
    private SurveyDataSource surveyDataSource;

    @Mock
    private SurveyQuestionResponseDataSource surveyQuestionResponseDataSource;

    @Mock
    private Clock clock;

    @InjectMocks
    private SurveyService surveyService;

    private static List<Survey> getMockData() {
        var questionTypeOption = new QuestionTypeOption(1L, null, 1, "option1");
        var questionType = new QuestionType(1L, "QuestionType", List.of(questionTypeOption));
        var question = new Question(1L, "dummy question", questionType);

        var survey = new Survey(1L, "test survey", Timestamp.valueOf("2020-01-01 00:00:00.000000000"), Timestamp.valueOf("2030-01-01 00:00:00.000000000"), new ArrayList<>());

        var surveyQuestion = new SurveyQuestion(survey, question, 1);
        surveyQuestion.setQuestion(question);

        survey.setConnections(List.of(surveyQuestion));
        return List.of(survey);
    }

    @Test
    public void shouldGetAllSurveys() {
        var mockData = getMockData();
        when(surveyDataSource.getAll()).thenReturn(mockData);

        assertThat(surveyService.getAllSurveys()).isEqualTo(mockData);
    }

    @Test
    public void shouldGetSurveyById() {
        var mockData = getMockData();
        Optional<Survey> expected = Optional.ofNullable(mockData.get(0));
        when(surveyDataSource.getById(1L)).thenReturn(expected);

        assertThat(surveyService.getForId(1L)).isEqualTo(expected);
    }

    @Test
    public void shouldReturnEmptyOptionalIfNotFound() {
        Optional<Survey> expected = Optional.empty();
        when(surveyDataSource.getById(1L)).thenReturn(expected);

        assertThat(surveyService.getForId(1L)).isEqualTo(expected);
    }

    @Test
    public void shouldSaveResponses() {
        var instant = Instant.now();
        var timestamp = Timestamp.from(instant);

        List<SurveyQuestionResponse> entities = List.of(
                new SurveyQuestionResponse(
                        1L,
                        1L,
                        1L,
                        1L,
                        null,
                        "response 1"
                ),
                new SurveyQuestionResponse(
                        2L,
                        1L,
                        2L,
                        1L,
                        null,
                        "response 2"
                )
        );

        List<SurveyQuestionResponse> entitiesWithTimestamps = List.of(
                new SurveyQuestionResponse(
                        1L,
                        1L,
                        1L,
                        1L,
                        timestamp,
                        "response 1"
                ),
                new SurveyQuestionResponse(
                        2L,
                        1L,
                        2L,
                        1L,
                        timestamp,
                        "response 2"
                )
        );

        when(Instant.now(clock)).thenReturn(instant);
        when(surveyQuestionResponseDataSource.saveResponses(Mockito.any())).thenReturn(entitiesWithTimestamps);

        var result = surveyService.saveSurveyResponses(entities);

        assertThat(result).isEqualTo(entitiesWithTimestamps);
    }
}