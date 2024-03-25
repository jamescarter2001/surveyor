package com.carter.surveyorbackend.data.source;

import com.carter.surveyorbackend.data.repo.SurveyQuestionResponseRepository;
import com.carter.surveyorbackend.data.repo.entity.SurveyQuestionResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class SurveyQuestionResponseDataSourceTest {

    @Mock
    private SurveyQuestionResponseRepository surveyQuestionResponseRepository;

    @InjectMocks
    private SurveyQuestionResponseDataSource surveyQuestionResponseDataSource;

    @Test
    public void shouldSubmitResponse() {
        var responses = List.of(
                new SurveyQuestionResponse(
                        1L,
                        1L,
                        1L,
                        1L,
                        Timestamp.valueOf("2020-01-01 00:00:00.000000000"),
                        "response"
                )
        );

        surveyQuestionResponseDataSource.saveResponses(responses);
        verify(surveyQuestionResponseRepository, times(1)).saveAll(responses);
    }
}
