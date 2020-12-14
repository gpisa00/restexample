package it.arteprogrammazione.restexample.commons.dto.ordersarticle;

import javax.validation.constraints.NotNull;

public class RequestOrderArticleDTO {

    @NotNull
    private Integer idOrder;

    @NotNull
    private Integer idArticle;

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
        return "RequestOrderArticleDTO{" +
                "idOrder=" + idOrder +
                ", idArticle=" + idArticle +
                '}';
    }
}
