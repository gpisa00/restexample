package it.arteprogrammazione.restexample.services.interfaces.paymentcards;

import it.arteprogrammazione.restexample.commons.dto.PaymentCardDTO;
import it.arteprogrammazione.restexample.commons.dto.RequestPaymentCardDTO;
import it.arteprogrammazione.restexample.commons.exceptions.customers.ConflictException;
import it.arteprogrammazione.restexample.commons.exceptions.customers.NotFoundException;
import org.springframework.hateoas.CollectionModel;

public interface IPaymentCardService {

    PaymentCardDTO findById(Integer idCustomer) throws NotFoundException;

    CollectionModel<PaymentCardDTO> findAll() throws NotFoundException;

    PaymentCardDTO save(RequestPaymentCardDTO request) throws ConflictException;
}
