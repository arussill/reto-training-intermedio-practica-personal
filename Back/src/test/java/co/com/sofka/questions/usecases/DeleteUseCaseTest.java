package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.reposioties.AnswerRepository;
import co.com.sofka.questions.reposioties.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class DeleteUseCaseTest {

    QuestionRepository questionRepository;
    AnswerRepository answerRepository;
    DeleteUseCase deleteUseCase;

    @BeforeEach
    public void setup() {
        questionRepository = mock(QuestionRepository.class);
        answerRepository = mock(AnswerRepository.class);
        deleteUseCase = new DeleteUseCase(answerRepository, questionRepository);
    }

    @Test
    void getValidationTest() {
        var question = new Question();
        question.setId("idPregunta");
        question.setUserId("usuarioPreguntaId");
        question.setType("tech");
        question.setCategory("software");
        question.setQuestion("¿Que es java?");


        Mono.just(question).flatMap(questionRepository::save).subscribe();

        when(questionRepository.deleteById(question.getId())).thenReturn(Mono.empty());

        StepVerifier.create(deleteUseCase.apply(question.getId()))
                .expectNextMatches(pregunta->{
                    assert pregunta.equals(question);
                    return true;
                }).expectComplete();

        verify(questionRepository).deleteById(question.getId());
    }



}