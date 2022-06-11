package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.reposioties.AnswerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class AddAnswerUseCaseTest {

    AnswerRepository answerRepository;
    AddAnswerUseCase addAnswerUseCase;
    GetUseCase getUseCase;

    @BeforeEach
    public void setup(){
        MapperUtils mapperUtils = new MapperUtils();
        answerRepository = mock(AnswerRepository.class);
        addAnswerUseCase = new AddAnswerUseCase(mapperUtils, getUseCase, answerRepository);
    }

    @Test
    void getValidationTest(){
        var answer = new Answer();
        answer.setId("respuestaId");
        answer.setUserId("idUsuario");
        answer.setQuestionId("idPregunta");
        answer.setAnswer("respuesta");
        answer.setPosition(1);
    }

//    when(answerRepository.save(answer)).thenReturn(Mono.just(answer));
}