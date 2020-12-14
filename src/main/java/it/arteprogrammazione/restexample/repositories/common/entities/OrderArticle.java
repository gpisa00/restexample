package it.arteprogrammazione.restexample.repositories.common.entities;

import javax.persistence.*;

@Table(name = "orders_articles")
@Entity
public class OrderArticle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int idOrder;

    private int idArticle;

    public OrderArticle() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public int getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(int idArticle) {
        this.idArticle = idArticle;
    }

    @Override
    public String toString() {
        return "OrderArticle{" +
                "idOrder=" + idOrder +
                ", idArticle=" + idArticle +
                '}';
    }
}
