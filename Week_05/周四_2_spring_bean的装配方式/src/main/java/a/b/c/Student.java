package a.b.c;

public class Student {
    private School school;
    private String name;

    public Student(School school, String name) {
        this.school = school;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name=" + name +
                ", myschool=" + school +
                '}';
    }
}
