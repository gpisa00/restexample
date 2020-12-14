package it.arteprogrammazione.restexample.services.implementations.customers.assemblers;

import it.arteprogrammazione.restexample.commons.dto.customers.CustomerDTO;
import it.arteprogrammazione.restexample.commons.dto.customers.RequestCustomerDTO;
import it.arteprogrammazione.restexample.commons.exceptions.commons.NotFoundException;
import it.arteprogrammazione.restexample.controllers.customers.CustomersRestController;
import it.arteprogrammazione.restexample.controllers.paymentcards.PaymentCardsRestController;
import it.arteprogrammazione.restexample.repositories.common.entities.Customer;
import it.arteprogrammazione.restexample.repositories.paymentcards.PaymentCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CustomerModelAssembler extends RepresentationModelAssemblerSupport<Customer, CustomerDTO> {

    private final PaymentCardRepository paymentCardRepository;

    @Autowired
    public CustomerModelAssembler(PaymentCardRepository paymentCardRepository) {
        super(CustomersRestController.class, CustomerDTO.class);
        this.paymentCardRepository = paymentCardRepository;
    }

    @Override
    public CustomerDTO toModel(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setFirstName(customer.getFirstName());
        customerDTO.setLastName(customer.getLastName());
        customerDTO.setOrganization(customer.getOrganization());
        Integer idCustomer = customer.getId();
        Link selfLink = linkTo(CustomersRestController.class).slash(idCustomer).withSelfRel();
        customerDTO.add(selfLink);
        try {
            if (paymentCardRepository.existsById(idCustomer)) {
                Link paymentCardLink = linkTo(methodOn(PaymentCardsRestController.class)
                        .findById(idCustomer)).withRel("payment_card");
                customerDTO.add(paymentCardLink);
            }
        } catch (NotFoundException ex) {
            //
        }
        return customerDTO;
    }

    public Customer toEntity(RequestCustomerDTO request) {
        Customer c = new Customer();
        c.setFirstName(request.getFirstName().toUpperCase());
        c.setLastName(request.getLastName().toUpperCase());
        c.setOrganization(request.getOrganization().toUpperCase());
        return c;
    }

    public static Customer toEntity(CustomerDTO customerDTO) {
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

}
