package it.arteprogrammazione.restexample.commons.utils;

import it.arteprogrammazione.restexample.repositories.customers.entities.Customer;
import it.arteprogrammazione.restexample.commons.dto.CustomerDTO;
import it.arteprogrammazione.restexample.commons.dto.RequestCustomerDTO;

public final class ConverterUtil {

    public static Customer convert(CustomerDTO customerDTO){
        Customer c = new Customer();
        c.setId(customerDTO.getId());
        c.setFirstName(customerDTO.getFirstName().toUpperCase());
        c.setLastName(customerDTO.getLastName().toUpperCase());

        c.setOrganization(
                customerDTO.getOrganization() != null &&
                        !customerDTO.getOrganization().isBlank() ?
                        customerDTO.getOrganization().toUpperCase() : null);
        return c;
    }

    public static CustomerDTO convert(Customer customer){
        CustomerDTO c = new CustomerDTO();
        c.setId(customer.getId());
        c.setFirstName(customer.getFirstName());
        c.setLastName(customer.getLastName());
        c.setOrganization(customer.getOrganization());
        return c;
    }

    public static Customer convert(RequestCustomerDTO request){
        Customer c = new Customer();
        c.setFirstName(request.getFirstName().toUpperCase());
        c.setLastName(request.getLastName().toUpperCase());
        c.setOrganization(request.getOrganization().toUpperCase());
        return c;
    }

}
