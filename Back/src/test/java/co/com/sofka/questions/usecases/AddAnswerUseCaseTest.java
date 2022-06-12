package co.com.sofka.questions.usecases;

        import co.com.sofka.questions.collections.Answer;
        import co.com.sofka.questions.collections.Question;
        import co.com.sofka.questions.reposioties.AnswerRepository;
        import co.com.sofka.questions.reposioties.QuestionRepository;
        import org.junit.jupiter.api.BeforeEach;
        import org.junit.jupiter.api.Test;
        import org.mockito.Mockito;
        import reactor.core.publisher.Flux;
        import reactor.core.publisher.Mono;
        import reactor.test.StepVerifier;

        import static org.mockito.Mockito.*;


class AddAnswerUseCaseTest {

    QuestionRepository questionRepository;
    AnswerRepository answerRepository;
    AddAnswerUseCase addAnswerUseCase;
    GetUseCase getUseCase;

    @BeforeEach
    public void setup() {
        MapperUtils mapperUtils = new MapperUtils();
        questionRepository = mock(QuestionRepository.class);
        answerRepository = mock(AnswerRepository.class);
        getUseCase = new GetUseCase(mapperUtils, questionRepository, answerRepository);
        addAnswerUseCase = new AddAnswerUseCase(mapperUtils, getUseCase, answerRepository);
    }

    @Test
    public void addAnswerTest() {
        MapperUtils mapper = new MapperUtils();
        var answer = new Answer();
        answer.setId("answerId");
        answer.setUserId("userId");
        answer.setQuestionId("questionId");
        answer.setAnswer("respuesta");
        answer.setPosition(0);

        var answerdto = mapper.mapEntityToAnswer().apply(answer);

        var question = new Question();
        question.setId("questionId");
        question.setUserId("userId");
        question.setQuestion("pregunta");
        question.setType("tech");
        question.setCategory("Software");

        when(questionRepository.findById(any(String.class))).thenReturn(Mono.just(question));
        when(answerRepository.save(any(Answer.class))).thenReturn(Mono.just(answer));
        when(answerRepository.findAllByQuestionId(any(String.class))).thenReturn(Flux.empty());

        StepVerifier.create(addAnswerUseCase.apply(answerdto))
                .expectNextMatches(questiondto -> {
                    assert questiondto.getId().equals("questionId");
                    assert questiondto.getUserId().equals("userId");
                    assert questiondto.getQuestion().equals("pregunta");
                    assert questiondto.getType().equals("tech");
                    assert questiondto.getCategory().equals("Software");
                    assert questiondto.getAnswers().contains(answerdto);
                    return true;
                })
                .verifyComplete();

        verify(questionRepository).findById("answerId");

    }


}
