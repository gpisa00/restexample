package it.arteprogrammazione.restexample.commons.exceptions.commons;

public class NotFoundException extends Exception {

    private static final long serialVersionUID = 3121196518520512147L;

    public NotFoundException(String message) {
        super(message);
    }
}
