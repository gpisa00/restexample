package it.arteprogrammazione.restexample.commons.dto;

import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Objects;

public class CustomerDTO extends RepresentationModel<CustomerDTO> implements Serializable{

    private static final long serialVersionUID = 4839698653028835403L;

    @NotNull
    private Integer id;

    @NotEmpty(message = "firstName may not be empty")
    @NotBlank(message = "firstName may not be blank")
    @Pattern(regexp = "^[A-Za-z]*$", message = "firstName may be only characters")
    private String firstName;

    @NotEmpty(message = "firstName may not be empty")
    @NotBlank(message = "firstName may not be blank")
    @Pattern(regexp = "^[A-Za-z]*$", message = "firstName may be only characters")
    private String lastName;

    private String organization;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
        return "CustomerDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", organization='" + organization + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerDTO that = (CustomerDTO) o;
        return firstName.equals(that.firstName) &&
                lastName.equals(that.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }
}
