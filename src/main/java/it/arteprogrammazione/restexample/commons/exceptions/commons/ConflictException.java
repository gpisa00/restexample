package it.arteprogrammazione.restexample.commons.exceptions.commons;

public class ConflictException extends Exception {

    private static final long serialVersionUID = 6670414450361419047L;

    public ConflictException(String message) {
        super(message);
    }
}
