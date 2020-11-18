package a.b.c;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AnnoConfiguration {
    @Bean
    School school() {
        School school = new School();
        school.setName("二中");
        return school;
    }

    @Bean
    Student student(School school) {
        return new Student(school, "studentAnno");
    }
}
