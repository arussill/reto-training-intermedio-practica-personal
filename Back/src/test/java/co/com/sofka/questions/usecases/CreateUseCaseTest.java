package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.QuestionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class CreateUseCaseTest {

    QuestionRepository questionRepository;
    CreateUseCase createUseCase;


    @BeforeEach
    public void setup() {
        MapperUtils mapperUtils = new MapperUtils();
        questionRepository = mock(QuestionRepository.class);
        createUseCase = new CreateUseCase(mapperUtils, questionRepository);
    }

    @Test
    void getValidationTest() {
        var question = new Question();
        question.setUserId("idUsuario");
        question.setType("tech");
        question.setCategory("software");
        question.setQuestion("¿Que es java?");

        var questionDTO = new QuestionDTO();
        questionDTO.setUserId("idUsuario");
        questionDTO.setType("tech");
        questionDTO.setCategory("software");
        questionDTO.setQuestion("¿Que es java?");

        when(questionRepository.save(question)).thenReturn(Mono.just(question));

        StepVerifier.create(createUseCase.apply(questionDTO))
                .(questionDTO->{
                    Assertions.assertEquals(questionDTO.getUserId(), question.getUserId());


                }).expectComplete();

        verify(questionRepository).save(question);
    }
}