package it.arteprogrammazione.restexample.services.implementations.orders.assemblers;

import it.arteprogrammazione.restexample.commons.dto.orders.OrderDTO;
import it.arteprogrammazione.restexample.commons.dto.orders.RequestOrderDTO;
import it.arteprogrammazione.restexample.commons.exceptions.commons.NotFoundException;
import it.arteprogrammazione.restexample.controllers.articles.ArticlesRestController;
import it.arteprogrammazione.restexample.controllers.customers.CustomersRestController;
import it.arteprogrammazione.restexample.controllers.orders.OrdersRestController;
import it.arteprogrammazione.restexample.repositories.common.entities.Order;
import it.arteprogrammazione.restexample.repositories.common.entities.OrderArticle;
import it.arteprogrammazione.restexample.repositories.customers.CustomerRepository;
import it.arteprogrammazione.restexample.repositories.ordersarticles.OrderArticleRepository;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderModelAssembler extends RepresentationModelAssemblerSupport<Order, OrderDTO> {

    private final OrderArticleRepository orderArticleRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public OrderModelAssembler(OrderArticleRepository orderArticleRepository,
                               CustomerRepository customerRepository) {
        super(OrdersRestController.class, OrderDTO.class);
        this.orderArticleRepository = orderArticleRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public OrderDTO toModel(Order order) {
        OrderDTO dto = new OrderDTO();
        Integer idOrder = order.getId();
        Integer idCustomer = order.getIdCustomer();

        dto.setId(idOrder);
        dto.setDeliveryDate(order.getDeliveryDate());
        dto.setPurchaseDate(order.getPurchaseDate());
        dto.setTotalPrice(order.getTotalPrice());
        dto.setIdCustomer(idCustomer);
        Link selfLink = linkTo(OrdersRestController.class).slash(idOrder).withSelfRel();
        dto.add(selfLink);

        try {

            if (customerRepository.existsById(idCustomer)) {
                Link customerLink = linkTo(methodOn(CustomersRestController.class)
                        .findById(idCustomer)).withRel("customer");
                dto.add(customerLink);
            }


            Iterable<OrderArticle> orderArticles = orderArticleRepository.findByIdOrder(idOrder);
            if (!IterableUtils.isEmpty(orderArticles)) {
                for (OrderArticle orderArticle : orderArticles) {
                    Link orderLink = linkTo(methodOn(ArticlesRestController.class)
                            .findById(orderArticle.getIdArticle())).withRel("articles");
                    dto.add(orderLink);
                }
            }

        } catch (NotFoundException ex) {
            //
        }
        return dto;
    }

    public Order toEntity(RequestOrderDTO request) {
        Order order = new Order();
        if (request.getId() != null)
            order.setId(request.getId());

        order.setDeliveryDate(request.getDeliveryDate());
        order.setPurchaseDate(request.getPurchaseDate());
        order.setIdCustomer(request.getIdCustomer());
        return order;
    }
}
