package eu.epptec;

public class Person {
    private final String firstName;
    private final String lastName;
    private final String birthNumber;

    public Person(String firstName, String lastName, String birthNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthNumber = birthNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBirthNumber() {
        return birthNumber;
    }
}
