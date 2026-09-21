package gr.epal.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseTest {

    public static void main(String[] args) {

        try (Connection connection = Database.connect()) {

            System.out.println("Η σύνδεση με τη SQLite βάση πέτυχε!");

//            DatabaseInitializer.initialize();
//
//            TestDataInitializer.insertTestData();

            String sql = """
                    SELECT id, first_name, last_name, active
                    FROM Teacher
                    ORDER BY last_name
                    """;

            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {

                System.out.println();
                System.out.println("=== ΚΑΘΗΓΗΤΕΣ ===");

                while (resultSet.next()) {

                    int id = resultSet.getInt("id");
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    boolean active = resultSet.getInt("active") == 1;

                    System.out.println(
                            id + " | "
                                    + lastName + " "
                                    + firstName + " | Active: "
                                    + active
                    );
                }
            }

        } catch (Exception e) {

            System.out.println("Αποτυχία σύνδεσης με τη βάση.");
            e.printStackTrace();
        }
    }
}