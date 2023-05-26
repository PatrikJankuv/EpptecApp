package eu.epptec;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

public class PersonsDatabase {
    private static final Map<String, Person> database = new HashMap<>();

    public void addPerson(Person person) {
        try {
            validateName(person.getFirstName(), "First name");
            validateName(person.getLastName(), "Last name");
            validateBirthNumber(person.getBirthNumber());

            if (database.containsKey(convertBirthNumberToTenDigits(person.getBirthNumber()))) {
                throw new PersonAlreadyExistsException();
            }

            database.put(convertBirthNumberToTenDigits(person.getBirthNumber()), person);
            System.out.println("The person has been successfully added to the database.");
        } catch (InvalidDataException | PersonAlreadyExistsException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removePerson(String birthNumber){
        try {
            if (!database.containsKey(convertBirthNumberToTenDigits(birthNumber))) {
                throw new PersonNotFoundException();
            }
            database.remove(convertBirthNumberToTenDigits(birthNumber));
            System.out.println("The person with the birth number " + birthNumber + " has been successfully removed from the database.");
        } catch (PersonNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public Person searchPersonByBirthNumber(String birthNumber){
        try {
            if (!database.containsKey(convertBirthNumberToTenDigits(birthNumber))) {
                throw new PersonNotFoundException();
            }
            return database.get(convertBirthNumberToTenDigits(birthNumber));
        } catch (PersonNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private static void validateName(String name, String fieldName) throws InvalidDataException {
        if (name.isEmpty()) {
            throw new InvalidDataException(fieldName + " must not be empty.");
        }
    }

    private static void validateBirthNumber(String birthNumber) throws InvalidDataException {
        // Validating the ID format (YYMMDDXXXX or YYMMDD/XXXX)
        if (!birthNumber.matches("\\d{6}(\\d{4}|/\\d{4})")) {
            throw new InvalidDataException("The birth number must be in the format YYMMDDXXXX or YYMMDD/XXXX.");
        }

        String yearPrefix = birthNumber.substring(0, 2);
        String month = birthNumber.substring(2, 4);
        String day = birthNumber.substring(4, 6);

        int year = Integer.parseInt(yearPrefix);
        int fullYear = 1900 + year;

        String dateStr = String.format("%d%s%s", fullYear, month, day);
        var formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        try {
            LocalDate dateOfBirth = LocalDate.parse(dateStr, formatter);
            LocalDate currentDate = LocalDate.now();

            if (dateOfBirth.isAfter(currentDate)) {
                throw new InvalidDataException("Invalid date of birth");
            }
        } catch (DateTimeParseException e) {
            throw new InvalidDataException("Invalid date of birth");
        }
    }

    private static String convertBirthNumberToTenDigits(String birthNumber){
        return birthNumber.length() <= 10 ? birthNumber : birthNumber.substring(0, 6) + birthNumber.substring(7);
    }
}
