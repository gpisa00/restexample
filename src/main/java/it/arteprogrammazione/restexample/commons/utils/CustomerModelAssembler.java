package it.arteprogrammazione.restexample.commons.utils;

import it.arteprogrammazione.restexample.commons.dto.CustomerDTO;
import it.arteprogrammazione.restexample.commons.exceptions.customers.NotFoundException;
import it.arteprogrammazione.restexample.controllers.customers.CustomersRestController;
import it.arteprogrammazione.restexample.controllers.paymentcards.PaymentCardsRestController;
import it.arteprogrammazione.restexample.repositories.common.entities.Customer;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CustomerModelAssembler extends RepresentationModelAssemblerSupport<Customer, CustomerDTO> {

    public CustomerModelAssembler() {
        super(CustomersRestController.class, CustomerDTO.class);
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
            Link paymentCardLink = linkTo(methodOn(PaymentCardsRestController.class)
                    .findByIdCustomer(idCustomer)).withRel("payment_card");
            customerDTO.add(paymentCardLink);
        }catch (NotFoundException ex){
            //
        }
        return customerDTO;
    }
}
