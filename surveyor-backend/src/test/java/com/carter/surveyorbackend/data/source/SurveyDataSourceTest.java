package com.carter.surveyorbackend.data.source;

import com.carter.surveyorbackend.data.repo.SurveyRepository;
import com.carter.surveyorbackend.data.repo.entity.Question;
import com.carter.surveyorbackend.data.repo.entity.QuestionType;
import com.carter.surveyorbackend.data.repo.entity.QuestionTypeOption;
import com.carter.surveyorbackend.data.repo.entity.Survey;
import com.carter.surveyorbackend.data.repo.entity.SurveyQuestion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SurveyDataSourceTest {

    @Mock
    private SurveyRepository surveyRepository;

    @InjectMocks
    private SurveyDataSource surveyDataSource;

    /**
     * Helper method for generating dummy survey data for testing.
     * @return A List of Survey objects.
     */
    private static List<Survey> getMockData() {
        var questionTypeOption = new QuestionTypeOption(1L, null, 1, "option1");
        var questionType = new QuestionType(1L, "LikertScale", List.of(questionTypeOption));
        var question = new Question(1L, "dummy question", questionType);

        var survey = new Survey(1L, "test survey", Timestamp.valueOf("2020-01-01 00:00:00.000000000"), Timestamp.valueOf("2030-01-01 00:00:00.000000000"), new ArrayList<>());

        var surveyQuestion = new SurveyQuestion(survey, question, 1);
        surveyQuestion.setQuestion(question);

        survey.setConnections(List.of(surveyQuestion));
        return List.of(survey);
    }

    /**
     * Test to verify that all surveys are returned correctly.
     */
    @Test
    public void shouldGetAllSurveys() {
        var mockData = getMockData();
        when(surveyRepository.findAll()).thenReturn(mockData);
        assertThat(surveyDataSource.getAll()).isEqualTo(mockData);
    }

    /**
     * Test to verify that a survey is returned when its id is specified.
     */
    @Test
    public void shouldGetSurveyById() {
        var mockData = getMockData();
        var expectedOptional = Optional.ofNullable(mockData.get(0));
        when(surveyRepository.findById(1L)).thenReturn(Optional.ofNullable(mockData.get(0)));
        assertThat(surveyDataSource.getById(1L)).isEqualTo(expectedOptional);
    }

    /**
     * Test to verify that an empty Optional object is returned when an invalid id is specified.
     */
    @Test
    public void shouldGetEmptyOptionalForMissingId() {
        when(surveyRepository.findById(2L)).thenReturn(Optional.empty());
        assertThat(surveyDataSource.getById(2L)).isEqualTo(Optional.empty());
    }
}
