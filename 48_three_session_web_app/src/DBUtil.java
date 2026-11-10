/*
 * Question 48: Develop a full-fledged web application with database access spreading over to 3 sessions.
 *
 * DBUtil opens JDBC connections to the MySQL database "labdb".
 * Change URL / USER / PASS here if your MySQL settings are different.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBUtil {

    private static final String URL  = "jdbc:mysql://localhost:3306/labdb";
    private static final String USER = "root";
    private static final String PASS = "root";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError(
                    "MySQL driver not found - copy mysql-connector-j jar to WEB-INF/lib");
        }
    }

    private DBUtil() { }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
