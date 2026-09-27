# Πρόγραμμα Εφημεριών ΕΠΑΛ

Desktop εφαρμογή για τη διαχείριση εκπαιδευτικών, ωρολογίου προγράμματος και αυτόματη δημιουργία προγράμματος εφημεριών.

## Τεχνολογίες

- Java 22
- JavaFX 21.0.6
- Maven
- SQLite
- SQLite JDBC

## Μέχρι τώρα

- Δημιουργήθηκε το Maven project.
- Ρυθμίστηκε και δοκιμάστηκε το JavaFX.
- Δημιουργήθηκε σύνδεση με SQLite.
- Δημιουργήθηκε το αρχικό database schema.
- Προστέθηκαν δοκιμαστικά δεδομένα.
- Δημιουργήθηκε η πρώτη JavaFX οθόνη για την εμφάνιση καθηγητών.
- Η εφαρμογή διαβάζει δεδομένα καθηγητών από τη SQLite και τα εμφανίζει σε TableView.

## Database

Βασικοί πίνακες:

- `Teacher`
- `Building`
- `Zone`
- `Room`
- `TimetableEntry`
- `DutyPost`
- `DutyAssignment`
- `ZonePriority`

## Δομή

```text
gr.epal
├── Main.java
├── database
│   ├── Database.java
│   ├── DatabaseInitializer.java
│   ├── DatabaseTest.java
│   └── TestDataInitializer.java
└── ui
    └── TeacherView.java
```

## Επόμενο βήμα

Διαχείριση καθηγητών:

- Προσθήκη
- Επεξεργασία
- Απενεργοποίηση / Επανενεργοποίηση
- Διαγραφή όπου επιτρέπεται
- Αναζήτηση

## Status

**Initial Prototype — Database and Teacher View completed.**
