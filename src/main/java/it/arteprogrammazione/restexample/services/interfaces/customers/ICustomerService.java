package it.arteprogrammazione.restexample.services.interfaces.customers;

import it.arteprogrammazione.restexample.commons.dto.CustomerDTO;
import it.arteprogrammazione.restexample.commons.dto.RequestCustomerDTO;
import it.arteprogrammazione.restexample.commons.exceptions.customers.ConflictException;
import it.arteprogrammazione.restexample.commons.exceptions.customers.NotFoundException;
import org.springframework.hateoas.CollectionModel;

public interface ICustomerService {

    CustomerDTO save(RequestCustomerDTO request) throws ConflictException;

    CustomerDTO findById(Integer id) throws NotFoundException;

    void deleteById(Integer id) throws NotFoundException;

    CustomerDTO update(CustomerDTO request) throws NotFoundException;

    CollectionModel<CustomerDTO> findAll() throws NotFoundException;
}
