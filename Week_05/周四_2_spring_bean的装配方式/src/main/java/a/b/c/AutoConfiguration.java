package a.b.c;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("a.b.c.e")
public class AutoConfiguration {
    @Bean
    String studentname() {
        return new String("三儿");
    }

    @Bean
    String schoolname() {
        return new String("三中");
    }
}
