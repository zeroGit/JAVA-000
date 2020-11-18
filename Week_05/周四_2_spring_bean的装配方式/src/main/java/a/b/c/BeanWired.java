package a.b.c;

import a.b.c.e.AutoSchool;
import a.b.c.e.AutoStudent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanWired {
    public static void main(String[] args) {
        // xml 配置方式
        XmlWired();

        // 注解配置方式
        AnnotationWired();

        // 自动装配
        AutoWired();
    }

    private static void XmlWired() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        School school = (School) context.getBean("school");
        System.out.println("xml - School : " + school);

        Student student = (Student) context.getBean("student");
        System.out.println("    - Student: " + student);
    }

    private static void AnnotationWired() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(AnnoConfiguration.class);
        context.refresh();

        School school = (School) context.getBean("school");
        System.out.println("Anno - School : " + school);

        Student student = (Student) context.getBean("student");
        System.out.println("     - Student: " + student);
    }

    private static void AutoWired() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(AutoConfiguration.class);
        context.refresh();

        AutoSchool school = (AutoSchool) context.getBean("autoSchool");
        System.out.println("Auto - School : " + school);

        AutoStudent student = (AutoStudent) context.getBean("autoStudent");
        System.out.println("     - Student: " + student);
    }
}
