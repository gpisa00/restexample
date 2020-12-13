package it.arteprogrammazione.restexample.services.implementations.customers;

import it.arteprogrammazione.restexample.commons.dto.CustomerDTO;
import it.arteprogrammazione.restexample.commons.dto.RequestCustomerDTO;
import it.arteprogrammazione.restexample.commons.exceptions.customers.ConflictException;
import it.arteprogrammazione.restexample.commons.exceptions.customers.NotFoundException;
import it.arteprogrammazione.restexample.services.implementations.customers.assemblers.CustomerModelAssembler;
import it.arteprogrammazione.restexample.repositories.customers.CustomerRepository;
import it.arteprogrammazione.restexample.repositories.common.entities.Customer;
import it.arteprogrammazione.restexample.services.interfaces.customers.ICustomerService;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;


@Service
public class CustomerService implements ICustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerModelAssembler customerModelAssembler;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, CustomerModelAssembler customerModelAssembler) {
        this.customerRepository = customerRepository;
        this.customerModelAssembler = customerModelAssembler;
    }

    @Override
    public CustomerDTO findById(Integer id) throws NotFoundException {
        Optional<Customer> result = customerRepository.findById(id);
        if(result.isEmpty())
            throw new NotFoundException("Customer "+ id +" not found");
        return customerModelAssembler.toModel(result.get());

    }

    @Override
    @Transactional
    public CustomerDTO save(RequestCustomerDTO request) throws ConflictException {

        if (customerRepository.existsByFirstNameAndLastName(request.getFirstName().toUpperCase(), request.getLastName().toUpperCase()))
            throw new ConflictException("Customer: " + request.getFirstName() + " " + request.getLastName() + " alredy exists");

        return customerModelAssembler.toModel(customerRepository.save(customerModelAssembler.toEntity(request)));
    }

    @Override
    @Transactional
    public void deleteById(Integer id) throws NotFoundException {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
        }else{
            throw new NotFoundException("Customer "+ id +" not found");
        }
    }

    @Override
    @Transactional
    public CustomerDTO update(CustomerDTO request) throws NotFoundException {
        if (customerRepository.existsById(request.getId())) {
            return  customerModelAssembler.toModel(customerRepository.save(customerModelAssembler.toEntity(request)));
        }else{
            throw new NotFoundException("Customer "+ request.getId() +" not found");
        }
    }

    @Override
    public CollectionModel<CustomerDTO> findAll() throws NotFoundException {
        Iterable<Customer> result = customerRepository.findAll();
        if(IterableUtils.isEmpty(result))
            throw new NotFoundException("Customers is Empty");
        return customerModelAssembler.toCollectionModel(result);
    }
}
