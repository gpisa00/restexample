package it.arteprogrammazione.restexample.services.implementations.paymentcards;

import it.arteprogrammazione.restexample.commons.dto.PaymentCardDTO;
import it.arteprogrammazione.restexample.commons.exceptions.customers.NotFoundException;
import it.arteprogrammazione.restexample.commons.utils.PaymentCardConverterUtil;
import it.arteprogrammazione.restexample.repositories.cardtypes.CardTypeRepository;
import it.arteprogrammazione.restexample.repositories.paymentcards.PaymentCardRepository;
import it.arteprogrammazione.restexample.repositories.common.entities.CardType;
import it.arteprogrammazione.restexample.repositories.common.entities.PaymentCard;
import it.arteprogrammazione.restexample.services.interfaces.paymentcards.IPaymentCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentCardService implements IPaymentCardService {

    private final PaymentCardRepository paymentCardRepository;

    private final CardTypeRepository cardTypeRepository;

    @Autowired
    public PaymentCardService(PaymentCardRepository paymentCardRepository, CardTypeRepository cardTypeRepository) {
        this.paymentCardRepository = paymentCardRepository;
        this.cardTypeRepository = cardTypeRepository;
    }


    @Override
    public PaymentCardDTO findByIdCustomer(Integer idCustomer) throws NotFoundException {

        Optional<PaymentCard> result = paymentCardRepository.findByIdCustomer(idCustomer);
        if(result.isEmpty())
            throw new NotFoundException("PaymentCards "+ idCustomer +" not found");

        PaymentCard p = result.get();
        Optional<CardType> cardType = cardTypeRepository.findById(p.getIdCardType());
        return PaymentCardConverterUtil.convert(p,cardType.get().getType());
    }
}
