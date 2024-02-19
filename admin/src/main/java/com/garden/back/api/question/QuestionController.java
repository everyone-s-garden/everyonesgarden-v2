package com.garden.back.api.question;

import com.garden.back.api.question.request.CreateQuestionRequest;
import com.garden.back.api.question.request.UpdateQuestionRequest;
import com.garden.back.domain.question.response.FindAllQuestions;
import com.garden.back.domain.question.response.FindQuestionDetails;
import com.garden.back.domain.question.QuestionService;
import com.garden.back.global.LocationBuilder;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/v1/admin/questions")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createFAQ(@RequestBody @Valid CreateQuestionRequest request) {
        URI uri = LocationBuilder.buildLocation(questionService.createQuestion(request.toServiceRequest()));

        return ResponseEntity.created(uri).build();
    }

    @PatchMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateFAQ(
        @PathVariable("id") Long id,
        @RequestBody @Valid UpdateQuestionRequest request
    ) {
        questionService.updateQuestion(request.toServiceRequest(id));

        return ResponseEntity.ok().build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FindAllQuestions> findAllQuestions() {
        return ResponseEntity.ok(questionService.findAll());
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FindQuestionDetails> findQuestionDetails(
        @PathVariable("id") Long id
    ) {
        return ResponseEntity.ok(questionService.findDetails(id));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteQuestion(
        @PathVariable("id") Long id
    ) {
        questionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }
}
