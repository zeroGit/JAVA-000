package a.b.c.e;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class AutoStudent {
    @Autowired
    private AutoSchool school;
    @Autowired
    @Qualifier("studentname")
    private String name;

    public AutoStudent() {
    }

    @Override
    public String toString() {
        return "Student{" +
                "name=" + name +
                ", myschool=" + school +
                '}';
    }
}
