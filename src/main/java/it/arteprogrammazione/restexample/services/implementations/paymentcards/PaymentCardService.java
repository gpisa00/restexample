package it.arteprogrammazione.restexample.services.implementations.paymentcards;

import it.arteprogrammazione.restexample.commons.dto.PaymentCardDTO;
import it.arteprogrammazione.restexample.commons.dto.RequestPaymentCardDTO;
import it.arteprogrammazione.restexample.commons.exceptions.customers.ConflictException;
import it.arteprogrammazione.restexample.commons.exceptions.customers.NotFoundException;
import it.arteprogrammazione.restexample.repositories.common.entities.PaymentCard;
import it.arteprogrammazione.restexample.repositories.customers.CustomerRepository;
import it.arteprogrammazione.restexample.repositories.paymentcards.PaymentCardRepository;
import it.arteprogrammazione.restexample.services.implementations.paymentcards.assemblers.PaymentCardModelAssembler;
import it.arteprogrammazione.restexample.services.interfaces.paymentcards.IPaymentCardService;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class PaymentCardService implements IPaymentCardService {

    private final PaymentCardRepository paymentCardRepository;
    private final CustomerRepository customerRepository;

    private final PaymentCardModelAssembler paymentCardModelAssembler;

    @Autowired
    public PaymentCardService(PaymentCardRepository paymentCardRepository,
                              CustomerRepository customerRepository,
                              PaymentCardModelAssembler paymentCardModelAssembler) {
        this.paymentCardRepository = paymentCardRepository;
        this.customerRepository = customerRepository;
        this.paymentCardModelAssembler = paymentCardModelAssembler;
    }

    @Override
    public PaymentCardDTO findById(Integer idCustomer) throws NotFoundException {

        Optional<PaymentCard> result = paymentCardRepository.findById(idCustomer);
        if (result.isEmpty())
            throw new NotFoundException("PaymentCards " + idCustomer + " not found");

        return paymentCardModelAssembler.toModel(result.get());
    }

    @Override
    public CollectionModel<PaymentCardDTO> findAll() throws NotFoundException {
        Iterable<PaymentCard> result = paymentCardRepository.findAll();
        if (IterableUtils.isEmpty(result))
            throw new NotFoundException("Payment Cards is empty");
        return paymentCardModelAssembler.toCollectionModel(result);
    }

    @Override
    @Transactional
    public PaymentCardDTO save(RequestPaymentCardDTO request) throws ConflictException {
        Integer idCustomer = request.getIdCustomer();
        if(!customerRepository.existsById(idCustomer))
            throw new ConflictException("Customer is not present: " + idCustomer);

        if (paymentCardRepository.existsById(idCustomer))
            throw new ConflictException("Payment card is present for customer: " + idCustomer);

        return paymentCardModelAssembler.toModel(paymentCardRepository.save(paymentCardModelAssembler.toEntity(request)));

    }
}
