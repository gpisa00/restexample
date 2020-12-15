package it.arteprogrammazione.restexample.commons.dto.orders;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.Date;

public class OrderDTO extends RepresentationModel<OrderDTO> implements Serializable {

    private static final long serialVersionUID = 3407343031043106538L;

    private Integer id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CEST")
    private Date deliveryDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CEST")
    private Date purchaseDate;

    private Integer totalPrice;

    private Integer idCustomer;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(Integer idCustomer) {
        this.idCustomer = idCustomer;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "id=" + id +
                ", deliveryDate=" + deliveryDate +
                ", purchaseDate=" + purchaseDate +
                ", totalPrice=" + totalPrice +
                ", idCustomer=" + idCustomer +
                '}';
    }
}
