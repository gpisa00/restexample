package it.arteprogrammazione.restexample.commons.dto.articles;

import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

public class ArticleDTO extends RepresentationModel<ArticleDTO> implements Serializable {

    private static final long serialVersionUID = -7410880931969305912L;

    private Integer id;

    private String description;

    private Integer price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ArticleDTO{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}
