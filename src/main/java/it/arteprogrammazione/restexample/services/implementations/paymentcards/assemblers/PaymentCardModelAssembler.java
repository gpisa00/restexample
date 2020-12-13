package it.arteprogrammazione.restexample.services.implementations.paymentcards.assemblers;

import it.arteprogrammazione.restexample.commons.dto.PaymentCardDTO;
import it.arteprogrammazione.restexample.commons.dto.RequestPaymentCardDTO;
import it.arteprogrammazione.restexample.commons.exceptions.customers.NotFoundException;
import it.arteprogrammazione.restexample.controllers.customers.CustomersRestController;
import it.arteprogrammazione.restexample.controllers.paymentcards.PaymentCardsRestController;
import it.arteprogrammazione.restexample.repositories.cardtypes.CardTypeRepository;
import it.arteprogrammazione.restexample.repositories.common.entities.PaymentCard;
import it.arteprogrammazione.restexample.repositories.customers.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PaymentCardModelAssembler extends RepresentationModelAssemblerSupport<PaymentCard, PaymentCardDTO> {

    private final CustomerRepository customerRepository;
    private final CardTypeRepository cardTypeRepository;

    @Autowired
    public PaymentCardModelAssembler(CustomerRepository customerRepository,
                                     CardTypeRepository cardTypeRepository) {
        super(PaymentCardsRestController.class, PaymentCardDTO.class);
        this.customerRepository = customerRepository;
        this.cardTypeRepository = cardTypeRepository;
    }

    @Override
    public PaymentCardDTO toModel(PaymentCard paymentCard) {
        PaymentCardDTO dto = new PaymentCardDTO();
        Integer idCustomer = paymentCard.getIdCustomer();
        dto.setIdCustomer(idCustomer);
        dto.setCardNumber(paymentCard.getCardNumber());
        dto.setType(cardTypeRepository.findById(paymentCard.getIdCardType()).get().getType());
        Link selfLink = linkTo(PaymentCardsRestController.class).slash(idCustomer).withSelfRel();
        dto.add(selfLink);
        try {
            if (customerRepository.existsById(idCustomer)) {
                Link customerLink = linkTo(methodOn(CustomersRestController.class)
                        .findById(idCustomer)).withRel("customer");
                dto.add(customerLink);
            }
        } catch (NotFoundException ex) {
            //
        }
        return dto;
    }

    public PaymentCard toEntity(RequestPaymentCardDTO request) {
        PaymentCard paymentCard = new PaymentCard();
        paymentCard.setIdCustomer(request.getIdCustomer());
        paymentCard.setCardNumber(request.getCardNumber());
        paymentCard.setIdCardType(request.getIdCardType());
        return paymentCard;
    }
}
