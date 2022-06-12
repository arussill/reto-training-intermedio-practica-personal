package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdateUseCaseTest {

    MapperUtils mapperUtils;
    QuestionRepository questionRepository;
    UpdateUseCase updateUseCase;

    @BeforeEach
    public void setUp(){
        questionRepository = mock(QuestionRepository.class);
        mapperUtils = new MapperUtils();
        updateUseCase = new UpdateUseCase(mapperUtils,questionRepository);

    }

    @Test
    public void setUpdateUseCase() {
        var question = new Question();
        question.setId("idPregunta");
        question.setUserId("ABCD1234");
        question.setQuestion("¿Que te parece el Training");
        question.setType("Aula Invertida");
        question.setCategory("Educación");

        var questionDTO = new QuestionDTO();
        questionDTO.setId(question.getId());
        questionDTO.setUserId(question.getUserId());
        questionDTO.setQuestion(question.getQuestion());
        questionDTO.setType("Abierta");
        questionDTO.setCategory("Matematicas");


        when(questionRepository.save(Mockito.any(Question.class))).thenReturn(Mono.just(question));

        StepVerifier.create(updateUseCase.apply(questionDTO))

                .expectNextMatches(q -> {

                    assert questionDTO.getId().equalsIgnoreCase(question.getId());
                    assert questionDTO.getUserId().equalsIgnoreCase("ABCD1234");
                    assert questionDTO.getCategory().equalsIgnoreCase("Matematicas");
                    assert questionDTO.getQuestion().equalsIgnoreCase("¿Que te parece el Training");
                    assert questionDTO.getType().equalsIgnoreCase("Abierta");
                    return true;
                }).verifyComplete();

        verify(questionRepository).save(Mockito.any(Question.class));
    }

}