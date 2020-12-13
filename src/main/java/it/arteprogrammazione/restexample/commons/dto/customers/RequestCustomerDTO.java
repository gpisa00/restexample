package it.arteprogrammazione.restexample.commons.dto.customers;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;


public class RequestCustomerDTO {

    @NotEmpty(message = "firstName may not be empty")
    @NotBlank(message = "firstName may not be blank")
    @Pattern(regexp = "^[A-Za-z]*$", message = "firstName may be only characters")
    private String firstName;

    @NotEmpty(message = "lastName may not be empty")
    @NotBlank(message = "lastName may not be blank")
    @Pattern(regexp = "^[A-Za-z]*$", message = "lastName may be only characters")
    private String lastName;

    private String organization;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    @Override
    public String toString() {
        return "RequestCustomerDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", organization='" + organization + '\'' +
                '}';
    }
}
