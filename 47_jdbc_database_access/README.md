# Q47 – Database Access using JDBC

**Question 47:** Creation of an application to have access from a database using JDBC.

`JdbcDemo.java` is a menu-driven console application that connects to a MySQL database
through JDBC and performs **C**reate / **R**ead / **U**pdate / **D**elete operations on a
`students` table.

## Files

| File | Purpose |
|------|---------|
| `JdbcDemo.java` | Console program – CREATE TABLE IF NOT EXISTS, INSERT, SELECT, UPDATE, DELETE, search |
| `db.sql` | Creates database `labdb`, table `students` and 3 sample rows |

## JDBC steps used in the program

1. **Load the driver** – `Class.forName("com.mysql.cj.jdbc.Driver")`
2. **Open a connection** – `DriverManager.getConnection("jdbc:mysql://localhost:3306/labdb", "root", "root")`
3. **Create a statement** – `Statement` for fixed SQL, `PreparedStatement` (with `?` placeholders) for user input
4. **Execute** – `executeUpdate()` for CREATE/INSERT/UPDATE/DELETE, `executeQuery()` for SELECT
5. **Process the `ResultSet`** – `while (rs.next()) { rs.getInt(...); rs.getString(...); }`
6. **Close** – done automatically by *try-with-resources*

## How to run

Requirements: JDK 8+, MySQL server, and the MySQL JDBC driver
(`mysql-connector-j-8.x.jar`, from <https://dev.mysql.com/downloads/connector/j/>).

```bash
# 1. Create the database and sample data (optional - the program creates the table itself,
#    but the database labdb must exist)
mysql -u root -p < db.sql

# 2. Compile (the driver is only needed at run time)
javac JdbcDemo.java

# 3. Run with the connector jar on the classpath
java -cp .:mysql-connector-j-8.x.jar JdbcDemo        # Linux / macOS
java -cp .;mysql-connector-j-8.x.jar JdbcDemo        # Windows
```

Database name, user and password are `labdb` / `root` / `root`; change the `URL`, `USER`
and `PASS` constants at the top of `JdbcDemo.java` if yours differ.

## Sample run

```
Connected to jdbc:mysql://localhost:3306/labdb
Table 'students' is ready.

===== STUDENT DATABASE (JDBC) =====
1. Insert student
2. Display all students
3. Update marks
4. Delete student
5. Search by roll no
0. Exit
Enter choice: 1
Roll no : 104
Name    : Arjun Das
Course  : MCA
Marks   : 72
1 record inserted.

Enter choice: 2
RollNo   Name                 Course     Marks
----------------------------------------------
101      Anu Mathew           MCA        85
102      Rahul Nair           MCA        78
103      Sneha Menon          MCA        91
104      Arjun Das            MCA        72
4 record(s) found.

Enter choice: 3
Roll no to update : 104
New marks         : 80
1 record updated.

Enter choice: 4
Roll no to delete : 102
1 record deleted.

Enter choice: 5
Roll no to search : 104
Name: Arjun Das, Course: MCA, Marks: 80

Enter choice: 0
Bye!
```
