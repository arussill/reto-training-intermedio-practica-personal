package co.com.sofka.questions.routers;

import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.usecases.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;


@RestController
@Configuration
public class QuestionRouter {
    //    getAll
    @Bean
    @RouterOperation(beanClass = ListUseCase.class, beanMethod = "get",
            operation = @Operation(operationId = "getAllQuestions", summary = "Obtener todas las preguntas", tags = {"Preguntas"}))
    public RouterFunction<ServerResponse> getAll(ListUseCase listUseCase) {
        return route(GET("/getAll"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(listUseCase.get(), QuestionDTO.class))
        );
    }

    //  getOwnerAll
    @Bean
    @RouterOperation(beanClass = OwnerListUseCase.class, beanMethod = "apply",
            operation = @Operation(operationId = "getOwnerAll", summary = "Obtener todas las preguntas de un usuario por su ID", tags = {"Preguntas"},
                    responses = {@ApiResponse(responseCode = "200", description = "successful operation"),
                            @ApiResponse(responseCode = "400", description = "Invalid supplied"),
                            @ApiResponse(responseCode = "404", description = "not found")}))
    public RouterFunction<ServerResponse> getOwnerAll(OwnerListUseCase ownerListUseCase) {
        return route(
                GET("/getOwnerAll/{userId}"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(
                                ownerListUseCase.apply(request.pathVariable("userId")),
                                QuestionDTO.class
                        ))
        );
    }

    // Post
    @Bean
    @RouterOperation(beanClass = CreateUseCase.class, beanMethod = "apply",
            operation = @Operation(operationId = "createQuestion", summary = "Crear pregunta",  tags = {"Preguntas"}))
    public RouterFunction<ServerResponse> create(CreateUseCase createUseCase) {
        Function<QuestionDTO, Mono<ServerResponse>> executor = questionDTO -> createUseCase.apply(questionDTO)
                .flatMap(result -> ServerResponse.ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .bodyValue(result));

        return route(
                POST("/create").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(QuestionDTO.class).flatMap(executor)
        );
    }

    //getById
    @Bean
    @RouterOperation(operation = @Operation(operationId = "QuestionDTO", summary = "Retorna una pregunta por ID", tags = {"Preguntas"},
            parameters = {@Parameter(in = ParameterIn.PATH, name = "id", description = "id")},
            responses = {@ApiResponse(responseCode = "200", description = "successful operaction",
                    content = @Content(schema = @Schema(implementation = QuestionDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid Question ID supplied"),
                    @ApiResponse(responseCode = "404", description = "Question not found")}))
    public RouterFunction<ServerResponse> get(GetUseCase getUseCase) {
        return route(
                GET("/get/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getUseCase.apply(
                                        request.pathVariable("id")),
                                QuestionDTO.class
                        ))
        );
    }

    // Post
    @Bean
    @RouterOperation(beanClass = AddAnswerUseCase.class, beanMethod = "apply",
            operation = @Operation(operationId = "addAnswer", summary = "agregar una respuesta",  tags = {"Respuestas"}))
    public RouterFunction<ServerResponse> addAnswer(AddAnswerUseCase addAnswerUseCase) {
        return route(POST("/add").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(AnswerDTO.class)
                        .flatMap(addAnswerDTO -> addAnswerUseCase.apply(addAnswerDTO)
                                .flatMap(result -> ServerResponse.ok()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                        )
        );
    }

    //Delete
    @Bean
    @RouterOperation(operation = @Operation(operationId = "deleteQuestionByID", summary = "Eliminar pregunta por ID", tags = {"Preguntas"},
            parameters = {@Parameter(in = ParameterIn.PATH, name = "id", description = "Question Id")},
            responses = {@ApiResponse(responseCode = "202", description = "successful operation"),
                    @ApiResponse(responseCode = "400", description = "Invalid Question ID supplied"),
                    @ApiResponse(responseCode = "404", description = "Question not found")}))
    public RouterFunction<ServerResponse> delete(DeleteUseCase deleteUseCase) {
        return route(
                DELETE("/delete/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.accepted()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(deleteUseCase.apply(request.pathVariable("id")), Void.class))
        );
    }
}
