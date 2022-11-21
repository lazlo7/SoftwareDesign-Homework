package net.requef.studentpicker;

import java.util.*;

public class Classroom {
    private static final Random RANDOM = new Random();

    private final List<Student> students;
    private final Map<Student, Integer> studentGrades = new HashMap<>();

    public Classroom(Student... students) {
        this.students = List.of(students);
    }

    public Map<Student, Integer> getStudentGrades() {
        return studentGrades;
    }

    public void setStudentGrade(Student student, int grade) {
        studentGrades.put(student, grade);
    }

    public Student getRandomStudent() {
        if (students.isEmpty()) {
            return null;
        }

        return students.get(RANDOM.nextInt(0, students.size()));
    }
}
