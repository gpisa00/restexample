package it.arteprogrammazione.restexample.services.implementations.customers;

import it.arteprogrammazione.restexample.commons.dto.CustomerDTO;
import it.arteprogrammazione.restexample.commons.dto.RequestCustomerDTO;
import it.arteprogrammazione.restexample.commons.exceptions.customers.ConflictException;
import it.arteprogrammazione.restexample.commons.exceptions.customers.NotFoundException;
import it.arteprogrammazione.restexample.commons.utils.CustomerConverterUtil;
import it.arteprogrammazione.restexample.repositories.customers.CustomerRepository;
import it.arteprogrammazione.restexample.repositories.common.entities.Customer;
import it.arteprogrammazione.restexample.services.interfaces.customers.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CustomerService implements ICustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerDTO findById(Integer id) throws NotFoundException {
        Optional<Customer> result = customerRepository.findById(id);
        if(result.isEmpty())
            throw new NotFoundException("Customer "+ id +" not found");
        return CustomerConverterUtil.convert(result.get());

    }

    @Override
    @Transactional
    public CustomerDTO save(RequestCustomerDTO request) throws ConflictException {

        if (customerRepository.existsByFirstNameAndLastName(request.getFirstName().toUpperCase(), request.getLastName().toUpperCase()))
            throw new ConflictException("Customer: " + request.getFirstName() + " " + request.getLastName() + " alredy exists");

        Customer c = CustomerConverterUtil.convert(request);

        return CustomerConverterUtil.convert(customerRepository.save(c));
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
            Customer c = CustomerConverterUtil.convert(request);
            return  CustomerConverterUtil.convert(customerRepository.save(c));
        }else{
            throw new NotFoundException("Customer "+ request.getId() +" not found");
        }
    }

    @Override
    public List<CustomerDTO> findAll() throws NotFoundException {
        List<CustomerDTO> result = StreamSupport.stream(
                customerRepository.findAll().spliterator(), false)
                .map(customer -> CustomerConverterUtil.convert(customer))
                .collect(Collectors.toList());
        if(result == null || result.isEmpty())
            throw new NotFoundException("Customers is Empty");
        return result;
    }
}
