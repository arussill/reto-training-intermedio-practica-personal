package co.com.sofka.questions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;


@EnableReactiveMongoRepositories
@SpringBootApplication
public class QuestionsApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuestionsApplication.class, args);
    }

}


