package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.AnswerRepository;
import co.com.sofka.questions.reposioties.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


import static org.mockito.Mockito.*;


class OwnerListUseCaseTest {

    QuestionRepository questionRepository;
    AnswerRepository answerRepository;
    OwnerListUseCase ownerListUseCase;

    @BeforeEach
    public void setup() {
        MapperUtils mapperUtils = new MapperUtils();
        answerRepository = mock(AnswerRepository.class);
        questionRepository = mock(QuestionRepository.class);
        ownerListUseCase = new OwnerListUseCase(mapperUtils, answerRepository);
    }

//    /*@Test
//    void getValidationTest() {
//
//        var question = new Question();
//        question.setId("idPregunta");
//        question.setUserId("userId");
//        question.setType("tech");
//        question.setCategory("software");
//        question.setQuestion("que es java?");
//
//        var questionDTO = new QuestionDTO(question.getId(),question.getUserId(),question.getQuestion(),question.getType(),question.getCategory());
//
//
//
//        when(questionRepository.save(Mockito.any(Question.class))).thenReturn(Mono.just(question));
//
//        when(questionRepository.findByUserId(questionDTO.getUserId())).thenReturn(Flux.just(question));
//
//        StepVerifier.create(ownerListUseCase.apply(question.getUserId()).collectList())
//                .expectNextMatches(preguntas -> {
//                    assert preguntas.get(0).getId().equals(question.getId());
//                    assert preguntas.get(0).getUserId().equals(question.getUserId());
//                    assert preguntas.get(0).getType().equals(question.getType());
//                    assert preguntas.get(0).getCategory().equals(question.getCategory());
//                    assert preguntas.get(0).getQuestion().equals(question.getQuestion());
//                    return true;
//                }).verifyComplete();
//
//        verify(questionRepository).findByUserId(question.getUserId());
//
//    }*/
}