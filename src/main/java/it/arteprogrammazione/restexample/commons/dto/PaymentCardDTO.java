package it.arteprogrammazione.restexample.commons.dto;

import java.io.Serializable;

public class PaymentCardDTO implements Serializable {

    private Integer idCustomer;

    private Integer cardNumber;

    private  String type;

    public Integer getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(Integer idCustomer) {
        this.idCustomer = idCustomer;
    }

    public Integer getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Integer cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
