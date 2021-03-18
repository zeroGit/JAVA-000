package io.kimmking.kmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class KmqApplication {

    public static void main(String[] args) {
        SpringApplication.run(KmqApplication.class, args);
    }
}
