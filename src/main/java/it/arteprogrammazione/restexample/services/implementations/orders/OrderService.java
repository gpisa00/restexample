package it.arteprogrammazione.restexample.services.implementations.orders;

import it.arteprogrammazione.restexample.commons.dto.orders.OrderDTO;
import it.arteprogrammazione.restexample.commons.dto.orders.RequestOrderDTO;
import it.arteprogrammazione.restexample.commons.exceptions.commons.ConflictException;
import it.arteprogrammazione.restexample.commons.exceptions.commons.NotFoundException;
import it.arteprogrammazione.restexample.repositories.common.entities.Order;
import it.arteprogrammazione.restexample.repositories.orders.OrderRepository;
import it.arteprogrammazione.restexample.repositories.ordersarticles.OrderArticleRepository;
import it.arteprogrammazione.restexample.services.implementations.orders.assemblers.OrderModelAssembler;
import it.arteprogrammazione.restexample.services.interfaces.orders.IOrderService;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final OrderArticleRepository orderArticleRepository;
    private final OrderModelAssembler orderModelAssembler;

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderArticleRepository orderArticleRepository, OrderModelAssembler orderModelAssembler) {
        this.orderRepository = orderRepository;
        this.orderArticleRepository = orderArticleRepository;
        this.orderModelAssembler = orderModelAssembler;
    }

    @Override
    public OrderDTO findById(Integer id) throws NotFoundException {
        Optional<Order> result = orderRepository.findById(id);
        if (result.isEmpty())
            throw new NotFoundException("Order " + id + " not found");
        return orderModelAssembler.toModel(result.get());
    }

    @Override
    public CollectionModel<OrderDTO> findAll() throws NotFoundException {
        Iterable<Order> result = orderRepository.findAll();
        if (IterableUtils.isEmpty(result))
            throw new NotFoundException("Orders is empty");
        return orderModelAssembler.toCollectionModel(result);
    }

    @Override
    @Transactional
    public OrderDTO save(RequestOrderDTO request, boolean update) throws ConflictException, NotFoundException {

        if (!update) {
            request.setId(null);
        } else {
            if (!orderRepository.existsById(request.getId()))
                throw new NotFoundException("Order not exists");
        }
        return orderModelAssembler.toModel(orderRepository.save(orderModelAssembler.toEntity(request)));
    }

    @Override
    @Transactional
    public void deleteById(Integer id) throws NotFoundException {
        if (!orderRepository.existsById(id))
            throw new NotFoundException("Order " + id + " not exixts");

        //Cancellazione tabelle correlate
        deleteOnTableRelation(id);
        //-------------------------------

        orderRepository.deleteById(id);
    }

    private void deleteOnTableRelation(Integer id) {

        if (!IterableUtils.isEmpty(orderArticleRepository.findByIdOrder(id)))
            orderArticleRepository.deleteByIdOrder(id);

    }
}