package com.garden.back.domain.question;

import com.garden.back.api.question.request.UpdateQuestionServiceRequest;
import com.garden.back.domain.question.request.CreateQuestionServiceRequest;
import com.garden.back.domain.question.response.FindAllQuestions;
import com.garden.back.domain.question.response.FindQuestionDetails;
import com.garden.back.global.IntegrationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@Transactional
class QuestionServiceTest extends IntegrationTestSupport {

    @Autowired
    QuestionService questionService;

    @Autowired
    QuestionRepository questionRepository;

    @DisplayName("질문을 생성한다.")
    @Test
    void createQuestion() {
        //given
        CreateQuestionServiceRequest request = new CreateQuestionServiceRequest("질문", QuestionType.CHAT, "답변");

        //when
        Long savedQuestionId = questionService.createQuestion(request);

        //then
        Question question = questionRepository.findById(savedQuestionId).orElseThrow(AssertionError::new);
        assertAll(
            () -> assertThat(question.getContent()).isEqualTo(request.content()),
            () -> assertThat(question.getQuestionAnswer()).isEqualTo(request.questionAnswer()),
            () -> assertThat(question.getQuestionType()).isEqualTo(request.questionType())
        );
    }

    @DisplayName("질문을 업데이트 한다.")
    @Test
    void updateQuestion() {
        //given
        Question given = Question.create("내용", QuestionType.CHAT, "답변");
        Long givenId = questionRepository.save(given).getId();
        UpdateQuestionServiceRequest request = new UpdateQuestionServiceRequest(givenId, "변경된 질문", QuestionType.CHAT, "변경된 답변");

        //when
        questionService.updateQuestion(request);

        //then
        Question question = questionRepository.findById(givenId).orElseThrow(AssertionError::new);

        assertAll(
            () -> assertThat(question.getContent()).isEqualTo(request.content()),
            () -> assertThat(question.getQuestionAnswer()).isEqualTo(request.questionAnswer()),
            () -> assertThat(question.getQuestionType()).isEqualTo(request.questionType())
        );
    }

    @DisplayName("모든 질문을 조회한다.")
    @Test
    void findAll() {
        //given
        Question given = Question.create("내용", QuestionType.CHAT, "답변");
        Long givenId = questionRepository.save(given).getId();
        FindAllQuestions findAllQuestions = new FindAllQuestions(List.of(new FindAllQuestions.QuestionInfo(givenId, given.getContent(), given.getQuestionType())));

        //when & then
        assertThat(questionService.findAll()).isEqualTo(findAllQuestions);
    }

    @DisplayName("질문의 상세 내용을 조회한다.")
    @Test
    void findDetails() {
        //given
        Question given = Question.create("내용", QuestionType.CHAT, "답변");
        Long givenId = questionRepository.save(given).getId();
        FindQuestionDetails findQuestionDetails = new FindQuestionDetails(given.getContent(), given.getQuestionAnswer());

        assertThat(questionService.findDetails(givenId)).isEqualTo(findQuestionDetails);
    }

    @DisplayName("질문을 삭제한다.")
    @Test
    void deleteQuestion() {
        //given
        Question given = Question.create("내용", QuestionType.CHAT, "답변");
        Long givenId = questionRepository.save(given).getId();

        //when
        questionService.deleteQuestion(givenId);

        //then
        assertThat(questionRepository.findById(givenId)).isEmpty();
    }
}