package com.carter.surveyorbackend.controller;

import com.carter.surveyorbackend.data.repo.entity.Question;
import com.carter.surveyorbackend.data.repo.entity.QuestionType;
import com.carter.surveyorbackend.data.repo.entity.QuestionTypeOption;
import com.carter.surveyorbackend.data.repo.entity.Survey;
import com.carter.surveyorbackend.data.repo.entity.SurveyQuestion;
import com.carter.surveyorbackend.data.repo.entity.SurveyQuestionResponse;
import com.carter.surveyorbackend.model.dto.SurveyQuestionResponseDTO;
import com.carter.surveyorbackend.service.SurveyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class SurveyControllerTest {

    @Mock
    private SurveyService surveyService;

    @InjectMocks
    private SurveyController surveyController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        mockMvc = MockMvcBuilders.standaloneSetup(surveyController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
                .build();
    }

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
    public void shouldGetAllSurveys() throws Exception {
        var mockData = getMockData();
        when(surveyService.getAllSurveys()).thenReturn(mockData);

        mockMvc.perform(get("/surveys")
                .accept(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.[0].surveyId").value(1),
                        jsonPath("$.[0].name").value("test survey"),
                        jsonPath("$.[0].startTime").value("2020-01-01T00:00:00.000+00:00"),
                        jsonPath("$.[0].endTime").value("2030-01-01T00:00:00.000+00:00"),
                        jsonPath("$.[0].connections[0].question.questionId").value(1),
                        jsonPath("$.[0].connections[0].question.body").value("dummy question"),
                        jsonPath("$.[0].connections[0].question.type.name").value("QuestionType")
                );
    }

    @Test
    public void shouldGetSurvey() throws Exception {
        var mockData = getMockData();
        when(surveyService.getForId(1L)).thenReturn(Optional.ofNullable(mockData.get(0)));

        mockMvc.perform(get("/surveys/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.surveyId").value(1),
                        jsonPath("$.name").value("test survey"),
                        jsonPath("$.startTime").value("2020-01-01T00:00:00.000+00:00"),
                        jsonPath("$.endTime").value("2030-01-01T00:00:00.000+00:00"),
                        jsonPath("$.connections[0].question.questionId").value(1),
                        jsonPath("$.connections[0].question.body").value("dummy question"),
                        jsonPath("$.connections[0].question.type.name").value("QuestionType")
                );
    }

    @Test
    public void shouldReturnNotFoundForInvalidSurveyId() throws Exception {
        when(surveyService.getForId(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/surveys/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldSubmitSurveyResponses() throws Exception {
        List<SurveyQuestionResponseDTO> dtos = List.of(
                SurveyQuestionResponseDTO.builder()
                        .surveyId("1")
                        .questionId("1")
                        .facilityId("1")
                        .response("response 1")
                        .build(),
                SurveyQuestionResponseDTO.builder()
                        .surveyId("1")
                        .questionId("2")
                        .facilityId("1")
                        .response("response 2")
                        .build()
        );

        List<SurveyQuestionResponse> entities = List.of(
                new SurveyQuestionResponse(
                        1L,
                        1L,
                        1L,
                        1L,
                        Timestamp.valueOf("2020-01-01 00:00:00.000000000"),
                        "response 1"
                ),
                new SurveyQuestionResponse(
                        2L,
                        1L,
                        2L,
                        1L,
                        Timestamp.valueOf("2020-01-01 00:00:00.000000000"),
                        "response 2"
                )
        );

        when(surveyService.saveSurveyResponses(Mockito.any())).thenReturn(entities);

        String json = new ObjectMapper().writeValueAsString(dtos);

        mockMvc.perform(post("/surveys/response")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Content-Type", "application/json")
                        .content(json))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.[0].surveyId").value(1),
                        jsonPath("$.[0].questionId").value(1),
                        jsonPath("$.[0].facilityId").value(1),
                        jsonPath("$.[0].timestamp").value("2020-01-01T00:00:00.000+00:00"),
                        jsonPath("$.[0].response").value("response 1"),
                        jsonPath("$.[1].surveyId").value(1),
                        jsonPath("$.[1].questionId").value(2),
                        jsonPath("$.[1].facilityId").value(1),
                        jsonPath("$.[1].timestamp").value("2020-01-01T00:00:00.000+00:00"),
                        jsonPath("$.[1].response").value("response 2")
                );
    }
}