package it.arteprogrammazione.restexample.services.implementations.paymentcards;

import it.arteprogrammazione.restexample.commons.dto.PaymentCardDTO;
import it.arteprogrammazione.restexample.commons.exceptions.customers.NotFoundException;
import it.arteprogrammazione.restexample.repositories.common.entities.PaymentCard;
import it.arteprogrammazione.restexample.repositories.paymentcards.PaymentCardRepository;
import it.arteprogrammazione.restexample.services.implementations.paymentcards.assemblers.PaymentCardModelAssembler;
import it.arteprogrammazione.restexample.services.interfaces.paymentcards.IPaymentCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentCardService implements IPaymentCardService {

    private final PaymentCardRepository paymentCardRepository;
    private final PaymentCardModelAssembler paymentCardModelAssembler;

    @Autowired
    public PaymentCardService(PaymentCardRepository paymentCardRepository,
                              PaymentCardModelAssembler paymentCardModelAssembler) {
        this.paymentCardRepository = paymentCardRepository;
        this.paymentCardModelAssembler = paymentCardModelAssembler;
    }

    @Override
    public PaymentCardDTO findById(Integer idCustomer) throws NotFoundException {

        Optional<PaymentCard> result = paymentCardRepository.findById(idCustomer);
        if (result.isEmpty())
            throw new NotFoundException("PaymentCards " + idCustomer + " not found");

        return paymentCardModelAssembler.toModel(result.get());
    }
}
