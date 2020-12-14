package it.arteprogrammazione.restexample.repositories.common.entities;

import javax.persistence.*;
import java.util.Date;

@Table(name = "orders")
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Date deliveryDate;

    private Date purchaseDate;

    private int totalPrice;

    private int idCustomer;

    public Order() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", deliveryDate=" + deliveryDate +
                ", purchaseDate=" + purchaseDate +
                ", totalPrice=" + totalPrice +
                ", idCustomer=" + idCustomer +
                '}';
    }
}
