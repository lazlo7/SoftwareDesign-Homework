package net.requef.studentpicker;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Populate classroom.
        Classroom classroom = new Classroom(
                new Student("Melon", "Usk"),
                new Student("Petr", "Petrov"),
                new Student("Ivan", "Ivanov"),
                new Student("Max", "Lenin"),
                new Student("Pavel", "Stalin"),
                new Student("Black", "Overlord"),
                new Student("Walter", "White"),
                new Student("Jesse", "Pinkman"),
                new Student("James", "May"),
                new Student("Jeremy", "Clarkson")
        );

        Scanner scanner = new Scanner(System.in);
        System.out.println("[type /h for help]");

        while (true) {
            System.out.print("Input command: ");
            final var cmd = scanner.nextLine();

            // Run the given command.
            switch (cmd) {
                case "/r" -> gradeRandomStudent(classroom);
                case "/l" -> printGradedStudents(classroom);
                case "/h" -> printUsage();
                case "/q" -> {
                    System.out.println("Goodbye.");
                    return;
                }
                default -> {
                    System.out.println("Unknown command: " + cmd);
                    printUsage();
                }
            }
        }
    }

    private static void gradeRandomStudent(Classroom classroom) {
        Scanner scanner = new Scanner(System.in);
        Student student = classroom.getRandomStudent();
        if (student == null) {
            System.out.println("No students are enrolled to this class.");
            return;
        }

        System.out.println("Chosen random student: " + student);
        System.out.printf("Is %s present? <yes/no> ", student);
        while (true) {
            String reply = scanner.nextLine();

            if ("yes".equalsIgnoreCase(reply)) {
                break;
            }

            if ("no".equalsIgnoreCase(reply)) {
                System.out.println("Student is absent - cannot set grade.");
                return;
            }

            System.out.print("Please provide a valid reply: <yes/no> ");
        }

        System.out.print("Please grade the student: <0-10> ");
        while (true) {
            int grade;
            try {
                grade = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Please provide an integer in range 0-10: ");
                continue;
            }

            if (grade >= 0 && grade <= 10) {
                classroom.setStudentGrade(student, grade);
                System.out.printf("%s has now grade: %s%n", student, grade);
                return;
            }

            System.out.print("Please provide an integer in range 0-10: ");
        }
    }

    private static void printGradedStudents(Classroom classroom) {
        System.out.println("Graded students:");

        if (classroom.getStudentGrades().isEmpty()) {
            System.out.println("<empty>");
            return;
        }

        for (final var entry : classroom.getStudentGrades().entrySet()) {
            System.out.printf("%s\t%s%n", entry.getKey(), entry.getValue());
        }
    }

    private static void printUsage() {
        System.out.println("Available commands:");
        System.out.println("\t/r - select a random student, ask if they are present");
        System.out.println("\t/l - list all students who received a grade");
        System.out.println("\t/q - exit the program");
        System.out.println("\t/h - print this message");
    }
}
