import a.b.c.User;
import a.b.c.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Hello {

    public static void main(String[] args) {
        Hello.mybatisTest();
    }

    private static void mybatisTest() {
        try {
            InputStream configStream = Resources.getResourceAsStream("config123.xml");
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(configStream);

            try (SqlSession session = factory.openSession()) {
                List<User> users = session.selectList("a.b.c.UserMapper.allUsers");
                System.out.println("--------------------------");
                users.forEach(u -> System.out.println(u.getUserType()));

                UserMapper mapper = session.getMapper(UserMapper.class);
                List<User> users1 = mapper.allUsers();
                System.out.println("++++++++++++++++++++++++++");
                users1.forEach(u -> System.out.println(u.getUserType()));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
