package it.arteprogrammazione.restexample.commons.exceptions.customers;

public class CustomerConflictException extends Exception{

    private static final long serialVersionUID = 6670414450361419047L;

    public CustomerConflictException(String message) {
        super(message);
    }
}
