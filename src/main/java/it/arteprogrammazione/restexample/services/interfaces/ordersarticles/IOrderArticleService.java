package it.arteprogrammazione.restexample.services.interfaces.ordersarticles;

import it.arteprogrammazione.restexample.commons.dto.ordersarticle.RequestOrderArticleDTO;
import it.arteprogrammazione.restexample.commons.exceptions.commons.NotFoundException;

public interface IOrderArticleService {

    void hookArticleToOrder(RequestOrderArticleDTO request) throws NotFoundException;

    void unhookArticleToOrder(Integer id) throws NotFoundException;

}
