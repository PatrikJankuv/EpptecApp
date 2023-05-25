package eu.epptec;

public class Main {
    public static void main(String[] args) {
        PersonsDatabase personsDatabase = new PersonsDatabase();
        CommandLineManager commandLineManager = new CommandLineManager(personsDatabase);
        commandLineManager.run();
    }
}
