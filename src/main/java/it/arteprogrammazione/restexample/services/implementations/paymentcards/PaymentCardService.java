package it.arteprogrammazione.restexample.services.implementations.paymentcards;

import it.arteprogrammazione.restexample.commons.dto.paymentcards.PaymentCardDTO;
import it.arteprogrammazione.restexample.commons.dto.paymentcards.RequestPaymentCardDTO;
import it.arteprogrammazione.restexample.commons.exceptions.commons.ConflictException;
import it.arteprogrammazione.restexample.commons.exceptions.commons.NotFoundException;
import it.arteprogrammazione.restexample.repositories.cardtypes.CardTypeRepository;
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
    private final CardTypeRepository cardTypeRepository;
    private final PaymentCardModelAssembler paymentCardModelAssembler;

    @Autowired
    public PaymentCardService(PaymentCardRepository paymentCardRepository,
                              CustomerRepository customerRepository,
                              CardTypeRepository cardTypeRepository,
                              PaymentCardModelAssembler paymentCardModelAssembler) {
        this.paymentCardRepository = paymentCardRepository;
        this.customerRepository = customerRepository;
        this.cardTypeRepository = cardTypeRepository;
        this.paymentCardModelAssembler = paymentCardModelAssembler;
    }

    @Override
    public PaymentCardDTO findById(Integer idCustomer) throws NotFoundException {

        Optional<PaymentCard> result = paymentCardRepository.findById(idCustomer);
        if (result.isEmpty())
            throw new NotFoundException("Payment Card " + idCustomer + " not found");

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
    public PaymentCardDTO save(RequestPaymentCardDTO request) throws ConflictException, NotFoundException {
        validateSaveRequest(request, false);
        return paymentCardModelAssembler.toModel(paymentCardRepository.save(paymentCardModelAssembler.toEntity(request)));

    }

    @Override
    @Transactional
    public void deleteById(Integer id) throws NotFoundException {
        if (!paymentCardRepository.existsById(id))
            throw new NotFoundException("Payment card " + id + " not exixts");
        paymentCardRepository.deleteById(id);
    }

    @Override
    @Transactional
    public PaymentCardDTO update(RequestPaymentCardDTO request) throws NotFoundException, ConflictException {
        validateSaveRequest(request, true);

        return paymentCardModelAssembler.toModel(paymentCardRepository.save(paymentCardModelAssembler.toEntity(request)));
    }

    //----------------------------------------------------------------------------------------------------

    private void validateSaveRequest(RequestPaymentCardDTO request, boolean update) throws ConflictException, NotFoundException {
        Integer idCustomer = request.getIdCustomer();

        if (!update && paymentCardRepository.existsById(idCustomer))
            throw new ConflictException("Payment card exists for customer: " + idCustomer);

        if(update && !paymentCardRepository.existsById(request.getIdCustomer()))
            throw new NotFoundException("Payment card not found");

        if (!customerRepository.existsById(idCustomer))
            throw new ConflictException("Customer not exists: " + idCustomer);

        if (!cardTypeRepository.existsById(request.getIdCardType()))
            throw new ConflictException("Card type not exists");


        if (paymentCardRepository.existsByCardNumber(request.getCardNumber()))
            throw new ConflictException("Payment card number already exists");
    }
}
