package main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        System.out.println("Новости Москвы - канал, где рассказываются про " +
                "главные новости города. Если хотите быть в курсе всего происходящего," +
                "то вам сюда." +
                "К слову, тим-лидом команды является действующий IT-специалист. " +
                "Подписывайтесь :-)");
    }
}
