package gr.epal.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initialize() {

        try (Connection connection = Database.connect();
             Statement statement = connection.createStatement()) {

            // Ενεργοποίηση Foreign Keys στο SQLite
            statement.execute("PRAGMA foreign_keys = ON");

            // 1. Teacher
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS Teacher (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        first_name TEXT NOT NULL,
                        last_name TEXT NOT NULL,
                        active INTEGER NOT NULL DEFAULT 1
                    )
                    """);

            // 2. Building
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS Building (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        name TEXT NOT NULL UNIQUE
                    )
                    """);

            // 3. Zone
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS Zone (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        name TEXT NOT NULL,
                        building_id INTEGER NOT NULL,
                        
                        FOREIGN KEY (building_id)
                            REFERENCES Building(id)
                    )
                    """);

            // 4. Room
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS Room (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        name TEXT NOT NULL,
                        zone_id INTEGER NOT NULL,
                        
                        FOREIGN KEY (zone_id)
                            REFERENCES Zone(id)
                    )
                    """);

            // 5. TimetableEntry
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS TimetableEntry (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        teacher_id INTEGER NOT NULL,
                        day INTEGER NOT NULL,
                        cycle INTEGER NOT NULL,
                        room_id INTEGER NOT NULL,
                        
                        FOREIGN KEY (teacher_id)
                            REFERENCES Teacher(id),
                            
                        FOREIGN KEY (room_id)
                            REFERENCES Room(id)
                    )
                    """);

            // 6. DutyPost
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS DutyPost (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        name TEXT NOT NULL UNIQUE,
                        zone_id INTEGER NOT NULL,
                        
                        FOREIGN KEY (zone_id)
                            REFERENCES Zone(id)
                    )
                    """);

            // 7. DutyAssignment
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS DutyAssignment (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        teacher_id INTEGER NOT NULL,
                        day INTEGER NOT NULL,
                        cycle INTEGER NOT NULL,
                        post_id INTEGER NOT NULL,
                        
                        FOREIGN KEY (teacher_id)
                            REFERENCES Teacher(id),
                            
                        FOREIGN KEY (post_id)
                            REFERENCES DutyPost(id),
                            
                        UNIQUE (teacher_id, day),
                        UNIQUE (post_id, day, cycle)
                    )
                    """);

            // 8. ZonePriority
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS ZonePriority (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        zone_id INTEGER NOT NULL,
                        preferred_zone_id INTEGER NOT NULL,
                        priority INTEGER NOT NULL,
                        
                        FOREIGN KEY (zone_id)
                            REFERENCES Zone(id),
                            
                        FOREIGN KEY (preferred_zone_id)
                            REFERENCES Zone(id),
                            
                        UNIQUE (zone_id, preferred_zone_id)
                    )
                    """);

            System.out.println("Η βάση δεδομένων δημιουργήθηκε επιτυχώς!");

        } catch (SQLException e) {

            System.out.println("Σφάλμα κατά τη δημιουργία της βάσης.");
            e.printStackTrace();
        }
    }
}