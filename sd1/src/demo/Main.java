package demo;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static String DB_URL = "jdbc:mysql://root:root123@localhost:3306/ex_joins";
    public static String SQL_QUERY = "SELECT * FROM employees WHERE salary> ?";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter DB username (<Enter> for 'root'): ");
        String username = scanner.nextLine().trim();
        username =username.length()>0? username:"root";
        System.out.println("Enter DB password (<Enter> for 'root'): ");
        String password = scanner.nextLine().trim();
        password =password.length()>0? password:"root123";

        //1.Load DB Driver
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.printf("Database driver: %s%n not found.",DB_DRIVER);
            System.exit(0);
        }
        System.out.println("DB Driver loaded successfully.");

        //2.Read query params
        System.out.println("Enter minimal salary (<Enter> for '1000'): ");
        String salaryStr = scanner.nextLine().trim();
        salaryStr =salaryStr.length()>0? salaryStr:"1000";
        double salary = 4000;
        try{
            salary=Double.parseDouble(salaryStr);
        }catch (NumberFormatException ex){
            System.err.printf("Invalid number: %s%n",salaryStr);
        }

        //3.Connect to DB
        Properties props = new Properties();
        props.setProperty("user",username);
        props.setProperty("password",password);

        try(Connection con=DriverManager.getConnection(DB_URL,props);
            PreparedStatement ps= con.prepareStatement(SQL_QUERY)) {

        System.out.printf("DB connection created successfully: %s%n",DB_URL);

           //5.Execute statement with parameter
            ps.setDouble(1,salary);
            ResultSet rs=ps.executeQuery();

            //6.Print results
            while (rs.next()) {
                System.out.printf("| %10d | %-15.15s | %-15.15s | %10.2f |%n",
                        rs.getLong("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getDouble("salary")
                );
            }
        } catch (SQLException e) {
            System.err.printf("Error closing DB connection %s",e.getMessage());
        }
    }
}
