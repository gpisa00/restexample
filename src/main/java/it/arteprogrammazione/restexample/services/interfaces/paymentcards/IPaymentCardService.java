package it.arteprogrammazione.restexample.services.interfaces.paymentcards;

import it.arteprogrammazione.restexample.commons.dto.paymentcards.PaymentCardDTO;
import it.arteprogrammazione.restexample.commons.dto.paymentcards.RequestPaymentCardDTO;
import it.arteprogrammazione.restexample.commons.exceptions.commons.ConflictException;
import it.arteprogrammazione.restexample.commons.exceptions.commons.NotFoundException;
import org.springframework.hateoas.CollectionModel;

public interface IPaymentCardService {

    PaymentCardDTO findById(Integer idCustomer) throws NotFoundException;

    CollectionModel<PaymentCardDTO> findAll() throws NotFoundException;

    PaymentCardDTO save(RequestPaymentCardDTO request) throws ConflictException, NotFoundException;

    void deleteById(Integer id) throws NotFoundException;

    PaymentCardDTO update(RequestPaymentCardDTO request) throws NotFoundException, ConflictException;
}
