import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;

public class JDBCTest {

    public static void main(String[] args) {
        jdbcSql();

        jdbcCall();

        hikariSql();
    }

    private static void jdbcSql() {
        Connection conn = null;
        try {

            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:33006/mydb", "root", "skskskskk");
            PreparedStatement ps = conn.prepareStatement("insert into user_data (user_id,user_name) values(?,?)");
            ps.setLong(1, 35345);
            ps.setString(2, "user1");
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

            // ps
        }
    }

    private static void jdbcCall() {

        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:31203/mysql", "root", "123456");

            String sqlstr = "call proc_insert_new_user(?,?";
            CallableStatement cs = conn.prepareCall(sqlstr);
            cs.setLong(1, 12345);
            cs.setString(2, "user2");
            cs.registerOutParameter(3, Types.INTEGER);
            cs.execute();

            int ret = cs.getInt(7);
            System.out.printf("jdbcCall ret : %d\n", ret);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

            // ps
        }
    }

    private static void hikariSql() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://127.0.0.1:33006/mydb");
        config.setUsername("root");
        config.setPassword("skksksksk");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        HikariDataSource ds = new HikariDataSource(config);
        Connection conn = null;
        try {

            conn = ds.getConnection();
            PreparedStatement ps = conn.prepareStatement("insert into user_data (user_id,user_name) values(?,?)");
            ps.setLong(1, 35345);
            ps.setString(2, "user1");
            ps.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }
}


