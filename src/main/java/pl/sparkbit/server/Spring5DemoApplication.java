package pl.sparkbit.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebReactive;
import org.springframework.web.reactive.config.WebReactiveConfigurer;

@EnableWebReactive
@Configuration
@SpringBootApplication(scanBasePackageClasses = {Spring5DemoApplication.class})
public class Spring5DemoApplication implements WebReactiveConfigurer {

    private static final Logger LOG = LoggerFactory.getLogger(Spring5DemoApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(Spring5DemoApplication.class, args);
    }

    @Bean
    public ObjectMapper jacksonObjectMapper() {
        return new ObjectMapper();
    }
}
