package it.arteprogrammazione.restexample.commons.exceptions.customers;

public class CustomerNotFoundException extends Exception{

    private static final long serialVersionUID = 3121196518520512147L;

    public CustomerNotFoundException(String message) {
        super(message);
    }
}
