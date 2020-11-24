package a.b.c.beans;

public class School {
    private final String name;

    public School(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "School{" +
                "name='" + name + '\'' +
                '}';
    }
}
