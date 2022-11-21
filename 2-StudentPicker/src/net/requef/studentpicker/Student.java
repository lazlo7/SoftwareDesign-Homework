package net.requef.studentpicker;

public record Student(String firstName, String lastName) {
    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
