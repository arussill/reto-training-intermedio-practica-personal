package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.reposioties.AnswerRepository;
import co.com.sofka.questions.reposioties.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class GetUseCaseTest {

    QuestionRepository questionRepository;
    AnswerRepository answerRepository;
    GetUseCase getUseCase;

    @BeforeEach
    public void setup() {
        MapperUtils mapperUtils = new MapperUtils();
        questionRepository = mock(QuestionRepository.class);
        answerRepository = mock(AnswerRepository.class);
        getUseCase = new GetUseCase(mapperUtils, questionRepository, answerRepository);
    }

    @Test
    void getValidationTest() {
        var question = new Question();
        question.setId("idPregunta");
        question.setUserId("usuarioPreguntaId");
        question.setType("tech");
        question.setCategory("software");
        question.setQuestion("¿Que es java?");

        var answer = new Answer();
        answer.setId("respuestaId");
        answer.setUserId("usuarioRespuestaId");
        answer.setQuestionId("idPregunta");
        answer.setPosition(1);
        answer.setAnswer("es un lenguaje de programación");

        when(questionRepository.findById(question.getId())).thenReturn(Mono.just(question));
        when(answerRepository.findAllByQuestionId(question.getId())).thenReturn(Flux.just(answer));

        StepVerifier.create(getUseCase.apply(question.getId()))
                .expectNextMatches(pregunta -> {
                    assert pregunta.getId().equals(question.getId());
                    assert pregunta.getUserId().equals(question.getUserId());
                    assert pregunta.getCategory().equals(question.getCategory());
                    assert pregunta.getQuestion().equals(question.getQuestion());
                    assert pregunta.getType().equals(question.getType());
                    return true;
                }).verifyComplete();

        verify(questionRepository).findById(question.getId());
        verify(answerRepository).findAllByQuestionId(question.getId());

    }
}