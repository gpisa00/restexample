package it.arteprogrammazione.restexample.services.interfaces.paymentcards;

import it.arteprogrammazione.restexample.commons.dto.PaymentCardDTO;
import it.arteprogrammazione.restexample.commons.exceptions.customers.NotFoundException;

public interface IPaymentCardService {

    PaymentCardDTO findByIdCustomer(Integer idCustomer) throws NotFoundException;
}