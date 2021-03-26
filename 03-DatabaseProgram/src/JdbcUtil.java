import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

/**
 * jdbc工具类
 *
 * @author Administrator
 */
public class JdbcUtil {
    private static String driver;
    private static String jdbcUrl;
    private static String username;
    private static String userpassword;

    static {
        //读取Properties文件
        ResourceBundle bundle = ResourceBundle.getBundle("jdbc");
        driver = bundle.getString("driver");
        jdbcUrl = bundle.getString("jdbcUrl");
        username = bundle.getString("username");
        userpassword = bundle.getString("userpassword");
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //获取Connection对象
    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(jdbcUrl, username, userpassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;

    }

    //关闭Statement
    public static void closeStatement(Statement state) {
        try {
            if (state != null) {
                state.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //关闭Connection
    public static void closeConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //关闭资源
    public static void closeResource(Statement state, Connection conn, ResultSet rs) {
        closeStatement(state);
        closeConnection(conn);
        closeResultSet(rs);
    }

    //关闭ResultSet连接
    public static void closeResultSet(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //事务回滚
    public static void rollback(Connection conn) {
        try {
            if (conn != null) {
                conn.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
