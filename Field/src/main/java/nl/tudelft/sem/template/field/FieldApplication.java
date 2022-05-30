package nl.tudelft.sem.template.field;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class FieldApplication {

    public static void main(String[] args) {
        SpringApplication.run(FieldApplication.class, args);
    }
}
