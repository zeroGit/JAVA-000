package a.b.c;

public class School {
    private String name;

    public School() {
    }

    @Override
    public String toString() {
        return "School{" +
                "name='" + name + '\'' +
                '}';
    }

    // ---setter getter------------
    public void setName(String name) {
        this.name = name;
    }

}
