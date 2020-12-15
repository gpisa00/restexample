package it.arteprogrammazione.restexample.services.interfaces.ordersarticles;

import it.arteprogrammazione.restexample.commons.dto.ordersarticle.OrderArticleDTO;
import it.arteprogrammazione.restexample.commons.dto.ordersarticle.RequestOrderArticleDTO;
import it.arteprogrammazione.restexample.commons.exceptions.commons.NotFoundException;
import org.springframework.hateoas.CollectionModel;

public interface IOrderArticleService {

    CollectionModel<OrderArticleDTO> findAll() throws NotFoundException;

    void hookArticleToOrder(RequestOrderArticleDTO request) throws NotFoundException;

    void unhookArticleToOrder(Integer id) throws NotFoundException;

}
