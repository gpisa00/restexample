package it.arteprogrammazione.restexample.services.implementations.customers;

import it.arteprogrammazione.restexample.commons.dto.customers.CustomerDTO;
import it.arteprogrammazione.restexample.commons.dto.customers.RequestCustomerDTO;
import it.arteprogrammazione.restexample.commons.exceptions.commons.ConflictException;
import it.arteprogrammazione.restexample.commons.exceptions.commons.NotFoundException;
import it.arteprogrammazione.restexample.repositories.common.entities.Customer;
import it.arteprogrammazione.restexample.repositories.common.entities.Order;
import it.arteprogrammazione.restexample.repositories.customers.CustomerRepository;
import it.arteprogrammazione.restexample.repositories.orders.OrderRepository;
import it.arteprogrammazione.restexample.repositories.ordersarticles.OrderArticleRepository;
import it.arteprogrammazione.restexample.repositories.paymentcards.PaymentCardRepository;
import it.arteprogrammazione.restexample.services.implementations.customers.assemblers.CustomerModelAssembler;
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
    private final PaymentCardRepository paymentCardRepository;
    private final OrderRepository orderRepository;
    private final OrderArticleRepository orderArticleRepository;

    private final CustomerModelAssembler customerModelAssembler;


    @Autowired
    public CustomerService(CustomerRepository customerRepository,
                           PaymentCardRepository paymentCardRepository,
                           OrderRepository orderRepository,
                           OrderArticleRepository orderArticleRepository,
                           CustomerModelAssembler customerModelAssembler) {
        this.customerRepository = customerRepository;
        this.paymentCardRepository = paymentCardRepository;
        this.orderRepository = orderRepository;
        this.orderArticleRepository = orderArticleRepository;
        this.customerModelAssembler = customerModelAssembler;
    }

    @Override
    public CustomerDTO findById(Integer id) throws NotFoundException {
        Optional<Customer> result = customerRepository.findById(id);
        if (result.isEmpty())
            throw new NotFoundException("Customer " + id + " not found");
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

        if (!customerRepository.existsById(id))
            throw new NotFoundException("Customer " + id + " not found");

        //Cancellazione tabelle correlate
        deleteOnTableRelation(id);
        //-------------------------------

        customerRepository.deleteById(id);
    }

    @Override
    @Transactional
    public CustomerDTO update(CustomerDTO request) throws NotFoundException {
        if (!customerRepository.existsById(request.getId()))
            throw new NotFoundException("Customer " + request.getId() + " not found");

        return customerModelAssembler.toModel(customerRepository.save(CustomerModelAssembler.toEntity(request)));
    }

    @Override
    public CollectionModel<CustomerDTO> findAll() throws NotFoundException {
        Iterable<Customer> result = customerRepository.findAll();
        if (IterableUtils.isEmpty(result))
            throw new NotFoundException("Customers is Empty");
        return customerModelAssembler.toCollectionModel(result);
    }

    //--------------------------------------------------------------------------------

    private void deleteOnTableRelation(Integer id) {
        if (paymentCardRepository.existsById(id))
            paymentCardRepository.deleteById(id);

        Iterable<Order> orders = orderRepository.findAll();
        if (!IterableUtils.isEmpty(orders)) {
            orders.forEach(order -> {
                Integer idOrder = order.getId();
                orderArticleRepository.deleteByIdOrder(idOrder);
                orderRepository.deleteById(idOrder);
            });
        }

    }

}
