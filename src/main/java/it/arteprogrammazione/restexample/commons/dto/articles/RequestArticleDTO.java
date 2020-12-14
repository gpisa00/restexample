package it.arteprogrammazione.restexample.commons.dto.articles;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class RequestArticleDTO {

    @NotNull(message = "id may not be empty")
    private Integer id;

    @NotEmpty(message = "description may not be empty")
    @NotBlank(message = "description may not be blank")
    private String description;

    @NotNull(message = "price may not be empty")
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
}
