package com.example.cooperation_project;

import java.util.TimeZone;
import javax.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaAuditing
/*@EnableScheduling*/
@SpringBootApplication
public class CooperationProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(CooperationProjectApplication.class, args);
    }
}
