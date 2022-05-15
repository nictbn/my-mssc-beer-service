package guru.springframework.mymsscbeerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisAutoConfiguration;

@SpringBootApplication
public class MyMsscBeerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyMsscBeerServiceApplication.class, args);
    }

}
