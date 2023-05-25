package eu.epptec;

import java.time.LocalDate;
import java.time.Period;
import java.util.Scanner;

public class CommandLineManager {
    private final PersonsDatabase db;
    private final Scanner scanner;

    public CommandLineManager(PersonsDatabase personsDatabase) {
        this.db = personsDatabase;
        this.scanner = new Scanner(System.in);
    }

    void run(){
        while (true) {
            System.out.println("Choose option:");
            System.out.println("1 add person");
            System.out.println("2 remove person");
            System.out.println("3 Search for a person by birth number");
            System.out.println("4 Exit the program");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    Person person = addPersonDialog();
                    db.addPerson(person);
                    break;
                case "2":
                    String birthNumber = removePersonDialog();
                    db.removePerson(birthNumber);
                    break;
                case "3":
                    String searchBirthNumber = searchPersonDialog();
                    Person searchPerson = db.searchPersonByBirthNumber(searchBirthNumber);
                    if(searchPerson != null) showPerson(searchPerson);
                    break;
                case "4":
                    System.out.println("The programme was closed.");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }

            System.out.println("Do you want to continue? (yes/no)");
            String continueChoice = scanner.nextLine();
            if (!continueChoice.equalsIgnoreCase("yes")) {
                System.out.println("The programme was closed.");
                break;
            }
        }
    }

    private Person addPersonDialog(){
        System.out.println("First name:");
        String firstName = scanner.nextLine();

        System.out.println("Last name:");
        String lastName = scanner.nextLine();

        System.out.println("Birth number (in format YYMMDDXXXX or YYMMDD/XXXX):");
        String birthNumber = scanner.nextLine();

        return new Person(firstName, lastName, birthNumber);
    }

    private String removePersonDialog(){
        System.out.println("Enter the birth number of the person you want to remove:");
        return scanner.nextLine();
    }

    private String searchPersonDialog(){
        System.out.println("Enter the birth number of the person you want to search for:");
        return scanner.nextLine();
    }

    private void showPerson(Person person){
        int age = calculateAge(person.getBirthNumber());

        System.out.println("First name: " + person.getFirstName());
        System.out.println("Last name: " + person.getLastName());
        System.out.println("Age: " + age);
    }

    public int calculateAge(String birthNumber){
        int twentyCentury = 1900;
        int twentyFirstCentury = 2000;

        String yearPrefix = birthNumber.substring(0, 2);
        String month = birthNumber.substring(2, 4);
        String day = birthNumber.substring(4, 6);

        int currentYear = LocalDate.now().getYear();
        int currentMonth = LocalDate.now().getMonthValue();
        int currentDay = LocalDate.now().getDayOfMonth();

        int year = Integer.parseInt(yearPrefix);
        //max age the app predict is 98 years
        //if the person was born in a year that has the same ending as the current year,
        //convert the year of birth to the current year
        int century = (year > currentYear - twentyFirstCentury) ? twentyCentury : twentyFirstCentury;
        int fullYear = century + year;

        LocalDate birthDate = LocalDate.of(fullYear, Integer.parseInt(month), Integer.parseInt(day));
        LocalDate currentDate = LocalDate.of(currentYear, currentMonth, currentDay);
        Period period = Period.between(birthDate, currentDate);

        return period.getYears();
        }
    }
