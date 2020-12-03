package it.arteprogrammazione.restexample.services.interfaces.customers;

import it.arteprogrammazione.restexample.commons.dto.CustomerDTO;
import it.arteprogrammazione.restexample.commons.dto.RequestCustomerDTO;
import it.arteprogrammazione.restexample.commons.exceptions.customers.CustomerConflictException;
import it.arteprogrammazione.restexample.commons.exceptions.customers.CustomerNotFoundException;

public interface ICustomerService {

        CustomerDTO save(RequestCustomerDTO request) throws CustomerConflictException;

        CustomerDTO findById(Integer id) throws CustomerNotFoundException;

        void deleteById(Integer id) throws CustomerNotFoundException;

        CustomerDTO update(CustomerDTO request) throws CustomerNotFoundException;
}
