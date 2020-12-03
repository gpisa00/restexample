package it.arteprogrammazione.restexample.services.implementations.customers;

import it.arteprogrammazione.restexample.commons.dto.CustomerDTO;
import it.arteprogrammazione.restexample.commons.dto.RequestCustomerDTO;
import it.arteprogrammazione.restexample.commons.exceptions.customers.CustomerConflictException;
import it.arteprogrammazione.restexample.commons.exceptions.customers.CustomerNotFoundException;
import it.arteprogrammazione.restexample.commons.utils.ConverterUtil;
import it.arteprogrammazione.restexample.repositories.customers.CustomerRepository;
import it.arteprogrammazione.restexample.repositories.customers.entities.Customer;
import it.arteprogrammazione.restexample.services.interfaces.customers.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class CustomerService implements ICustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerDTO findById(Integer id) throws CustomerNotFoundException {
        Optional<Customer> result = customerRepository.findById(id);
        if(result.isEmpty())
            throw new CustomerNotFoundException("Customer "+ id +" not found");
        return ConverterUtil.convert(result.get());

    }

    @Override
    @Transactional
    public CustomerDTO save(RequestCustomerDTO request) throws CustomerConflictException {

        if (customerRepository.existsByFirstNameAndLastName(request.getFirstName().toUpperCase(), request.getLastName().toUpperCase()))
            throw new CustomerConflictException("Customer: " + request.getFirstName() + " " + request.getLastName() + " alredy exists");

        Customer c = ConverterUtil.convert(request);

        return ConverterUtil.convert(customerRepository.save(c));
    }

    @Override
    @Transactional
    public void deleteById(Integer id) throws CustomerNotFoundException {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
        }else{
            throw new CustomerNotFoundException("Customer "+ id +" not found");
        }
    }

    @Override
    @Transactional
    public CustomerDTO update(CustomerDTO request) throws CustomerNotFoundException {
        if (customerRepository.existsById(request.getId())) {
            Customer c = ConverterUtil.convert(request);
            return  ConverterUtil.convert(customerRepository.save(c));
        }else{
            throw new CustomerNotFoundException("Customer "+ request.getId() +" not found");
        }
    }
}
