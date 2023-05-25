package eu.epptec;

class InvalidDataException extends Exception {
    public InvalidDataException(String message) {
        super(message);
    }
}

class PersonNotFoundException extends Exception {
    public PersonNotFoundException() {
        super("Person not found");
    }
}

class PersonAlreadyExistsException extends Exception {
    public PersonAlreadyExistsException() {
        super("Person already exists");
    }
}