package it.arteprogrammazione.restexample.commons.utils;

import it.arteprogrammazione.restexample.commons.dto.PaymentCardDTO;
import it.arteprogrammazione.restexample.repositories.common.entities.PaymentCard;

public final class PaymentCardConverterUtil {

    public static PaymentCardDTO convert(PaymentCard paymentCard, String type) {
        PaymentCardDTO dto = new PaymentCardDTO();
        dto.setIdCustomer(paymentCard.getIdCustomer());
        dto.setCardNumber(paymentCard.getCardNumber());
        dto.setType(type);
        return dto;
    }

}
