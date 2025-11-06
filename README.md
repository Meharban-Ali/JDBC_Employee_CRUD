# JDBC_Employee_CRUD
A simple and beginner-friendly Java JDBC project demonstrating CRUD (Create, Read, Update, Delete) operations with MySQL Database. Perfect for students learning database connectivity in Java.
This project demonstrates how to connect a **Java program** with a **MySQL database** using **JDBC (Java Database Connectivity)** and perform an **UPDATE** operation securely with a `PreparedStatement`.

## 📘 Table of Contents
- About the Project
- Features
- Technologies Used
- Database Setup
- Project Setup
- How to Run
- Code Explanation.
- Security Note
- Future Improvements
- Author
- License

## 📖 About the Project
Write here a short paragraph explaining what your program does.  
For example:
> This program updates a employee's record (name, email, mobileNo, role, and address) in the `employee` table based on the employee ID entered by the user.  
> It uses JDBC to connect to a MySQL database and demonstrates secure query execution using `PreparedStatement`.

## ✨ Features
- Connects Java with MySQL database  
- Takes input from user via console  
- Updates data safely using `PreparedStatement`  
- Handles invalid IDs and SQL errors gracefully  
- Demonstrates basic CRUD operation (Update part)


  ## ⚙️ Technologies Used
| Tool | Purpose |
|------|----------|
| Java (JDK 8+) | Programming language |
| MySQL | Database |
| JDBC | Database Connectivity API |
| MySQL Connector/J | JDBC Driver for MySQL |
| VS Code / IntelliJ | Code editor |

## 🗄️ Database Setup
Use the following SQL commands to create your database and table:

sql
CREATE DATABASE IF NOT EXISTS employee_data;
USE employee_data;

CREATE TABLE IF NOT EXISTS employee_details (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  email VARCHAR(100) NOT NULL,
  mobileNo VARCHAR(100) NOT NULL,
  role VARCHAR(100) NOT NULL,
  address VARCHAR(100)
);

Add sample data if you want to: 
INSERT INTO employee_details(name, email, mobileNo, role, address) VALUES('Meharban', 'meharbanali123@gmail.com', '7505195381', 'Java Developer', 'New Delhi');

🧩 Project Setup
1-Clone or download this repository.
2-Create a lib/ folder and place the mysql-connector-j-8.x.x.jar file inside it.
3-Create a dbconfig.properties file in the project root:
and add inside dbconfig.properties file.
db.url=jdbc:mysql://localhost:3306/student_data
db.username=root
db.password=your_password_here
4-Create .gitignore file and add inside .gitignore file
dbconfig.properties
lib/*.jar



🧠 Code Explanation
Here’s how the program works step-by-step:
Takes employee ID and new details (name, email, mobileNo, role, address) as input.
Loads JDBC driver (com.mysql.cj.jdbc.Driver).
Connects to database using credentials from dbconfig.properties.
Prepares SQL update query:
Binds user input safely using PreparedStatement.
Executes update and shows success/failure message.
Closes all database resources properly.

✅ All mysql database queries for Perform CRUD operation.
1-Insert-Query: String query = 'INSERT INTO employee (name, email, mobileNo, role, address) VALUES('Meharban', 'meharbanali123@gmail.com', '7505195381', 'Java Developer', 'New Delhi');
2-Update-Query: 'UPDATE employee_details SET name = ?, email = ?, mobileNo = ?, role = ?, address = ? where id = ?'
3-Delete-Query: 'DELETE FROM employee_details WHERE ID= ?'
4-Get-Query: 'SELECT * FROM employee_details'


🔒 Security Note
Database password is stored in dbconfig.properties (which is ignored in .gitignore).
Never push sensitive files (like passwords) to GitHub.
Use environment variables or Secret Manager for production apps.

🚀 Future Improvements
Add Create, Read, Delete functionality (Full CRUD).
Use try-with-resources to auto-close DB connections.
Convert to Maven project (for dependency management).
Add logging and validation.


👨‍💻 Author
Meharban Ali
📧 meharbanali7900@gmail.com
💻 Java Fullstack Developer (Learning Path)
