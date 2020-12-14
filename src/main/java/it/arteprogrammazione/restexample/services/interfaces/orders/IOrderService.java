package it.arteprogrammazione.restexample.services.interfaces.orders;

import it.arteprogrammazione.restexample.commons.dto.orders.OrderDTO;
import it.arteprogrammazione.restexample.commons.dto.orders.RequestOrderDTO;
import it.arteprogrammazione.restexample.commons.exceptions.commons.ConflictException;
import it.arteprogrammazione.restexample.commons.exceptions.commons.NotFoundException;
import org.springframework.hateoas.CollectionModel;

public interface IOrderService {

    OrderDTO findById(Integer id) throws NotFoundException;

    CollectionModel<OrderDTO> findAll() throws NotFoundException;

    OrderDTO save(RequestOrderDTO request, boolean update) throws ConflictException, NotFoundException;

    void deleteById(Integer id) throws NotFoundException;

}
