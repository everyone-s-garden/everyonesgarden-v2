package com.garden.back.domain.question;

import com.garden.back.api.question.request.UpdateQuestionServiceRequest;
import com.garden.back.domain.question.response.FindAllQuestions;
import com.garden.back.domain.question.request.CreateQuestionServiceRequest;
import com.garden.back.domain.question.response.FindQuestionDetails;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Long createQuestion(CreateQuestionServiceRequest request) {
        Question savedQuestion = questionRepository.save(request.toEntity());
        return savedQuestion.getId();
    }

    @Transactional
    public void updateQuestion(UpdateQuestionServiceRequest request) {
        Question question = questionRepository.findById(request.id()).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 id"));
        question.update(request.content(), request.questionType(), request.questionAnswer());
    }

    public FindAllQuestions findAll() {
        return FindAllQuestions.from(
            questionRepository.findAll()
                .stream()
                .map(FindAllQuestions.QuestionInfo::from)
                .toList()
        );
    }

    public FindQuestionDetails findDetails(Long id) {
        return questionRepository.findById(id)
            .map(FindQuestionDetails::from)
            .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 id"));
    }

    public void deleteQuestion(Long id) {
        Question question = questionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 id"));
        questionRepository.delete(question);
    }
}
