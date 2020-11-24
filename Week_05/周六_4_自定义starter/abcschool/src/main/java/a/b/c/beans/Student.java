package a.b.c.beans;

public class Student {
    private final String name;
    private final School school;

    public Student(String name, School school) {
        this.name = name;
        this.school = school;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", myschool=" + school +
                '}';
    }
}
