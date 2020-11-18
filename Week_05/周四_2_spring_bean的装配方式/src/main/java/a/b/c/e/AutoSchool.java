package a.b.c.e;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AutoSchool {
    @Autowired
    @Qualifier("schoolname")
    private String name;

    public AutoSchool() {
    }

    @Override
    public String toString() {
        return "AutoSchool{" +
                "name='" + name + '\'' +
                '}';
    }

    // ---setter getter------------
    public void setName(String name) {
        this.name = name;
    }

}
