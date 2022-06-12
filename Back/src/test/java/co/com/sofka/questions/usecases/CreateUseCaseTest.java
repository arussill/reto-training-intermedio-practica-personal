package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class CreateUseCaseTest {


    QuestionRepository questionRepository;
    CreateUseCase createUseCase;

    @BeforeEach
    public void setUp() {
        MapperUtils mapperUtils = new MapperUtils();
        questionRepository = mock(QuestionRepository.class);
        createUseCase = new CreateUseCase(mapperUtils, questionRepository);

    }

    @Test
    void getValidationCreateTest() {
        var question = new Question();
        question.setId("idPregunta");
        question.setUserId("userId");
        question.setQuestion("pregunta");
        question.setType("educativa");
        question.setCategory("tech");

        var questionDTO = new QuestionDTO();
        questionDTO.setId(question.getId());
        questionDTO.setUserId(question.getUserId());
        questionDTO.setQuestion(question.getQuestion());
        questionDTO.setType(question.getType());
        questionDTO.setCategory(question.getCategory());

        when(questionRepository.save(Mockito.any(Question.class))).thenReturn(Mono.just(question));

        StepVerifier.create(createUseCase.apply(questionDTO))

                .expectNextMatches(q -> {

                    assert questionDTO.getId().equals(question.getId());
                    assert questionDTO.getUserId().equals("userId");
                    assert questionDTO.getCategory().equals("tech");
                    assert questionDTO.getQuestion().equals("pregunta");
                    assert questionDTO.getType().equals("educativa");
                    return true;
                }).verifyComplete();

        verify(questionRepository).save(Mockito.any(Question.class));
    }
}