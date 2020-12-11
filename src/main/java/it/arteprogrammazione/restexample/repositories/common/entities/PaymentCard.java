package it.arteprogrammazione.restexample.repositories.common.entities;

import javax.persistence.*;

@Table(name = "payment_cards")
@Entity
public class PaymentCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCustomer;

    private int cardNumber;

    private int idCardType;

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getIdCardType() {
        return idCardType;
    }

    public void setIdCardType(int idCardType) {
        this.idCardType = idCardType;
    }

    @Override
    public String toString() {
        return "PaymentCard{" +
                "idCustomer=" + idCustomer +
                ", cardNumber=" + cardNumber +
                ", idCardType=" + idCardType +
                '}';
    }
}
