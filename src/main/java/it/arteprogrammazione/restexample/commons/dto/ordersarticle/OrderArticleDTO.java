package it.arteprogrammazione.restexample.commons.dto.ordersarticle;

import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

public class OrderArticleDTO extends RepresentationModel<OrderArticleDTO> implements Serializable {

    private Integer id;

    private Integer idOrder;

    private Integer idArticle;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Integer idOrder) {
        this.idOrder = idOrder;
    }

    public Integer getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(Integer idArticle) {
        this.idArticle = idArticle;
    }

    @Override
    public String toString() {
        return "OrderArticleDTO{" +
                "id=" + id +
                ", idOrder=" + idOrder +
                ", idArticle=" + idArticle +
                '}';
    }
}
