package demo;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Football {
    public static void main(String[] args) {
        //1.Read props from external property file
        Properties props = new Properties();
        String path = Football.class.getClassLoader()
                .getResource("jdbc.properties")
                .getPath();
        System.out.printf("Resource path: %s%n", path);

        path = URLDecoder.decode(path, StandardCharsets.UTF_8);
        System.out.printf("Decoded resource path: %s%n", path);


        try {
            props.load(new FileInputStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // TODO: add meaningful defaults
        System.out.println(props);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter player name (<Enter> for 'Cristiano'): ");
        String username = scanner.nextLine().trim();
        username = username.length() > 0 ? username : "Cristiano";


        //2.try with resources - Connection, PreparedStatement
        try (Connection con = DriverManager.getConnection(
                props.getProperty("db.url"),
                props.getProperty("db.user"),
                props.getProperty("db.password"));
             PreparedStatement ps = con.prepareStatement(props.getProperty("sql.teams"))) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            //3. Print data
            while (rs.next()) {
                if (rs.getLong("id") == 0) {
                    System.out.printf("DB user with player name '%s' not found.", username);
                } else {
                    System.out.printf("| %10d | %-15.15s | %-15.15s | %10d |%n",
                            rs.getLong("id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getInt("count"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
