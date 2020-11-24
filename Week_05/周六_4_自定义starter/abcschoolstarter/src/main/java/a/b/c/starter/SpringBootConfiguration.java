package a.b.c.starter;

import a.b.c.starter.prop.SchoolProp;
import a.b.c.starter.prop.StudentProp;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import a.b.c.beans.School;
import a.b.c.beans.Student;

@Configuration
@EnableConfigurationProperties({SchoolProp.class, StudentProp.class})
@ConditionalOnProperty(prefix = "a.b.c.school", name = "enable", havingValue = "true")
public class SpringBootConfiguration {
    private final SchoolProp schoolProp;
    private final StudentProp studentProp;

    // 自动注入 SchoolProp StudentProp
    public SpringBootConfiguration(SchoolProp schoolProp, StudentProp studentProp) {
        this.schoolProp = schoolProp;
        this.studentProp = studentProp;
    }

    @Bean
    School school() {
        return new School(schoolProp.getName());
    }

    @Bean
    Student student() {
        return new Student(studentProp.getName(), school());
    }

}
