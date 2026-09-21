package gr.epal.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class TestDataInitializer {

    public static void insertTestData() {

        try (Connection connection = Database.connect()) {

            connection.setAutoCommit(false);

            insertTeachers(connection);
            insertBuildings(connection);
            insertZones(connection);
            insertRooms(connection);
            insertDutyPosts(connection);
            insertZonePriorities(connection);
            insertTimetable(connection);

            connection.commit();

            System.out.println("Τα δοκιμαστικά δεδομένα προστέθηκαν επιτυχώς!");

        } catch (SQLException e) {

            System.out.println("Σφάλμα κατά την εισαγωγή των δοκιμαστικών δεδομένων.");
            e.printStackTrace();
        }
    }

    private static void insertTeachers(Connection connection) throws SQLException {

        String sql = """
                INSERT INTO Teacher (first_name, last_name, active)
                VALUES (?, ?, ?)
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            addTeacher(statement, "Γιώργος", "Παπαδόπουλος", 1);
            addTeacher(statement, "Μαρία", "Δημητρίου", 1);
            addTeacher(statement, "Νίκος", "Ιωαννίδης", 1);
            addTeacher(statement, "Ελένη", "Γεωργίου", 1);
            addTeacher(statement, "Αλέξανδρος", "Κωνσταντίνου", 1);
        }
    }

    private static void addTeacher(
            PreparedStatement statement,
            String firstName,
            String lastName,
            int active) throws SQLException {

        statement.setString(1, firstName);
        statement.setString(2, lastName);
        statement.setInt(3, active);
        statement.executeUpdate();
    }

    private static void insertBuildings(Connection connection) throws SQLException {

        String sql = """
                INSERT INTO Building (name)
                VALUES (?)
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            addBuilding(statement, "Κτίριο Α");
            addBuilding(statement, "Κτίριο Β");
        }
    }

    private static void addBuilding(
            PreparedStatement statement,
            String name) throws SQLException {

        statement.setString(1, name);
        statement.executeUpdate();
    }

    private static void insertZones(Connection connection) throws SQLException {

        String sql = """
                INSERT INTO Zone (name, building_id)
                VALUES (?, ?)
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            addZone(statement, "Ζώνη Α1", 1);
            addZone(statement, "Ζώνη Α2", 1);
            addZone(statement, "Ζώνη Β1", 2);
            addZone(statement, "Ζώνη Β2", 2);
        }
    }

    private static void addZone(
            PreparedStatement statement,
            String name,
            int buildingId) throws SQLException {

        statement.setString(1, name);
        statement.setInt(2, buildingId);
        statement.executeUpdate();
    }

    private static void insertRooms(Connection connection) throws SQLException {

        String sql = """
                INSERT INTO Room (name, zone_id)
                VALUES (?, ?)
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            addRoom(statement, "Α101", 1);
            addRoom(statement, "Α102", 1);

            addRoom(statement, "Α201", 2);
            addRoom(statement, "Α202", 2);

            addRoom(statement, "Β101", 3);
            addRoom(statement, "Β102", 3);

            addRoom(statement, "Β201", 4);
            addRoom(statement, "Β202", 4);
        }
    }

    private static void addRoom(
            PreparedStatement statement,
            String name,
            int zoneId) throws SQLException {

        statement.setString(1, name);
        statement.setInt(2, zoneId);
        statement.executeUpdate();
    }

    private static void insertDutyPosts(Connection connection) throws SQLException {

        String sql = """
                INSERT INTO DutyPost (name, zone_id)
                VALUES (?, ?)
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            addDutyPost(statement, "Α-1", 1);
            addDutyPost(statement, "Α-2", 2);
            addDutyPost(statement, "Β-1", 3);
            addDutyPost(statement, "Β-2", 4);
        }
    }

    private static void addDutyPost(
            PreparedStatement statement,
            String name,
            int zoneId) throws SQLException {

        statement.setString(1, name);
        statement.setInt(2, zoneId);
        statement.executeUpdate();
    }

    private static void insertZonePriorities(Connection connection) throws SQLException {

        String sql = """
                INSERT INTO ZonePriority
                    (zone_id, preferred_zone_id, priority)
                VALUES (?, ?, ?)
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            // Κτίριο Α
            addZonePriority(statement, 1, 2, 1);
            addZonePriority(statement, 2, 1, 1);

            // Κτίριο Β
            addZonePriority(statement, 3, 4, 1);
            addZonePriority(statement, 4, 3, 1);
        }
    }

    private static void addZonePriority(
            PreparedStatement statement,
            int zoneId,
            int preferredZoneId,
            int priority) throws SQLException {

        statement.setInt(1, zoneId);
        statement.setInt(2, preferredZoneId);
        statement.setInt(3, priority);
        statement.executeUpdate();
    }

    private static void insertTimetable(Connection connection) throws SQLException {

        String sql = """
                INSERT INTO TimetableEntry
                    (teacher_id, day, cycle, room_id)
                VALUES (?, ?, ?, ?)
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            /*
             * day:
             * 1 = Δευτέρα
             * 2 = Τρίτη
             * 3 = Τετάρτη
             * 4 = Πέμπτη
             * 5 = Παρασκευή
             *
             * cycle:
             * 1 = Κύκλος 1
             * 2 = Κύκλος 2
             */

            // Γιώργος Παπαδόπουλος
            addTimetableEntry(statement, 1, 1, 1, 1);
            addTimetableEntry(statement, 1, 1, 2, 3);
            addTimetableEntry(statement, 1, 2, 1, 1);
            addTimetableEntry(statement, 1, 3, 2, 2);
            addTimetableEntry(statement, 1, 4, 1, 1);
            addTimetableEntry(statement, 1, 5, 2, 3);

            // Μαρία Δημητρίου
            addTimetableEntry(statement, 2, 1, 1, 3);
            addTimetableEntry(statement, 2, 1, 2, 4);
            addTimetableEntry(statement, 2, 2, 1, 3);
            addTimetableEntry(statement, 2, 3, 2, 4);
            addTimetableEntry(statement, 2, 4, 1, 4);
            addTimetableEntry(statement, 2, 5, 2, 3);

            // Νίκος Ιωαννίδης
            addTimetableEntry(statement, 3, 1, 1, 5);
            addTimetableEntry(statement, 3, 2, 2, 6);
            addTimetableEntry(statement, 3, 3, 1, 5);
            addTimetableEntry(statement, 3, 4, 2, 6);
            addTimetableEntry(statement, 3, 5, 1, 5);

            // Ελένη Γεωργίου
            // Έχει μάθημα και στα δύο κτίρια.
            addTimetableEntry(statement, 4, 1, 1, 1);
            addTimetableEntry(statement, 4, 1, 2, 7);
            addTimetableEntry(statement, 4, 2, 1, 2);
            addTimetableEntry(statement, 4, 3, 2, 8);
            addTimetableEntry(statement, 4, 4, 1, 1);
            addTimetableEntry(statement, 4, 5, 2, 7);

            // Αλέξανδρος Κωνσταντίνου
            // Σκόπιμα δεν έχει ωρολόγιο πρόγραμμα.
        }
    }

    private static void addTimetableEntry(
            PreparedStatement statement,
            int teacherId,
            int day,
            int cycle,
            int roomId) throws SQLException {

        statement.setInt(1, teacherId);
        statement.setInt(2, day);
        statement.setInt(3, cycle);
        statement.setInt(4, roomId);
        statement.executeUpdate();
    }
}