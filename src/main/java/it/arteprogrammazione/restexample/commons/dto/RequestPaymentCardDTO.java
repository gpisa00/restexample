package it.arteprogrammazione.restexample.commons.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class RequestPaymentCardDTO {

    @NotNull(message = "idCustomer may not be empty")
    @Pattern(regexp = "^\\d+$")
    private Integer idCustomer;

    @NotNull(message = "cardNumber may not be empty")
    @Pattern(regexp = "^\\d+$")
    private Integer cardNumber;

    @NotNull(message = "idCardType may not be empty")
    @Pattern(regexp = "^\\d+$")
    private Integer idCardType;

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

    public Integer getIdCardType() {
        return idCardType;
    }

    public void setIdCardType(Integer idCardType) {
        this.idCardType = idCardType;
    }

    @Override
    public String toString() {
        return "RequestPaymentCardDTO{" +
                "idCustomer=" + idCustomer +
                ", cardNumber=" + cardNumber +
                ", idCardType=" + idCardType +
                '}';
    }
}
