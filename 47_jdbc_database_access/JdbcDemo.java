/*
 * Question 47: Creation of an application to have access from a database using JDBC.
 *
 * JdbcDemo is a menu-driven console program that connects to MySQL (database labdb)
 * and performs the basic CRUD operations on a "students" table:
 *   1. Insert a student   (PreparedStatement INSERT)
 *   2. Display all        (SELECT)
 *   3. Update marks       (PreparedStatement UPDATE)
 *   4. Delete a student   (PreparedStatement DELETE)
 *   5. Search by roll no  (PreparedStatement SELECT ... WHERE)
 *   0. Exit
 * The table is created automatically with CREATE TABLE IF NOT EXISTS.
 *
 * Compile : javac JdbcDemo.java
 * Run     : java -cp .:mysql-connector-j-8.x.jar JdbcDemo      (Windows: use ; instead of :)
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class JdbcDemo {

    // Change these if your MySQL settings are different
    private static final String URL  = "jdbc:mysql://localhost:3306/labdb";
    private static final String USER = "root";
    private static final String PASS = "root";

    public static void main(String[] args) {
        try {
            // Step 1: load the driver (optional for JDBC 4+, kept for clarity)
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC driver not found. Add mysql-connector-j jar to the classpath.");
            return;
        }

        // Step 2: open the connection (try-with-resources closes it automatically)
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             Scanner sc = new Scanner(System.in)) {

            System.out.println("Connected to " + URL);
            createTable(con);

            int choice;
            do {
                System.out.println("\n===== STUDENT DATABASE (JDBC) =====");
                System.out.println("1. Insert student");
                System.out.println("2. Display all students");
                System.out.println("3. Update marks");
                System.out.println("4. Delete student");
                System.out.println("5. Search by roll no");
                System.out.println("0. Exit");
                System.out.print("Enter choice: ");
                choice = readInt(sc);

                switch (choice) {
                    case 1: insert(con, sc);  break;
                    case 2: display(con);     break;
                    case 3: update(con, sc);  break;
                    case 4: delete(con, sc);  break;
                    case 5: search(con, sc);  break;
                    case 0: System.out.println("Bye!"); break;
                    default: System.out.println("Invalid choice");
                }
            } while (choice != 0);

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    /** CREATE TABLE IF NOT EXISTS students */
    private static void createTable(Connection con) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS students ("
                + " roll_no INT PRIMARY KEY,"
                + " name    VARCHAR(50) NOT NULL,"
                + " course  VARCHAR(30),"
                + " marks   INT)";
        try (Statement st = con.createStatement()) {
            st.executeUpdate(sql);
            System.out.println("Table 'students' is ready.");
        }
    }

    /** INSERT using PreparedStatement */
    private static void insert(Connection con, Scanner sc) {
        System.out.print("Roll no : "); int roll = readInt(sc);
        System.out.print("Name    : "); String name = sc.nextLine().trim();
        System.out.print("Course  : "); String course = sc.nextLine().trim();
        System.out.print("Marks   : "); int marks = readInt(sc);

        String sql = "INSERT INTO students (roll_no, name, course, marks) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, roll);
            ps.setString(2, name);
            ps.setString(3, course);
            ps.setInt(4, marks);
            int rows = ps.executeUpdate();
            System.out.println(rows + " record inserted.");
        } catch (SQLException e) {
            System.out.println("Insert failed: " + e.getMessage());
        }
    }

    /** SELECT all rows and print them as a table */
    private static void display(Connection con) {
        String sql = "SELECT roll_no, name, course, marks FROM students ORDER BY roll_no";
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            System.out.printf("%-8s %-20s %-10s %-5s%n", "RollNo", "Name", "Course", "Marks");
            System.out.println("----------------------------------------------");
            int count = 0;
            while (rs.next()) {
                System.out.printf("%-8d %-20s %-10s %-5d%n",
                        rs.getInt("roll_no"), rs.getString("name"),
                        rs.getString("course"), rs.getInt("marks"));
                count++;
            }
            System.out.println(count + " record(s) found.");
        } catch (SQLException e) {
            System.out.println("Select failed: " + e.getMessage());
        }
    }

    /** UPDATE marks of a student */
    private static void update(Connection con, Scanner sc) {
        System.out.print("Roll no to update : "); int roll = readInt(sc);
        System.out.print("New marks         : "); int marks = readInt(sc);

        String sql = "UPDATE students SET marks = ? WHERE roll_no = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, marks);
            ps.setInt(2, roll);
            int rows = ps.executeUpdate();
            System.out.println(rows == 0 ? "No such student." : rows + " record updated.");
        } catch (SQLException e) {
            System.out.println("Update failed: " + e.getMessage());
        }
    }

    /** DELETE a student */
    private static void delete(Connection con, Scanner sc) {
        System.out.print("Roll no to delete : "); int roll = readInt(sc);

        String sql = "DELETE FROM students WHERE roll_no = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, roll);
            int rows = ps.executeUpdate();
            System.out.println(rows == 0 ? "No such student." : rows + " record deleted.");
        } catch (SQLException e) {
            System.out.println("Delete failed: " + e.getMessage());
        }
    }

    /** SELECT ... WHERE roll_no = ? */
    private static void search(Connection con, Scanner sc) {
        System.out.print("Roll no to search : "); int roll = readInt(sc);

        String sql = "SELECT name, course, marks FROM students WHERE roll_no = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, roll);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Name: " + rs.getString("name")
                            + ", Course: " + rs.getString("course")
                            + ", Marks: " + rs.getInt("marks"));
                } else {
                    System.out.println("No such student.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Search failed: " + e.getMessage());
        }
    }

    /** Reads a whole line and converts it to int; asks again on bad input. */
    private static int readInt(Scanner sc) {
        while (true) {
            if (!sc.hasNextLine()) {
                return 0;                  // end of input -> behave like "Exit"
            }
            String line = sc.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.print("Please enter a number: ");
            }
        }
    }
}
