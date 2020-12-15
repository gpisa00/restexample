package it.arteprogrammazione.restexample.services.implementations.ordersarticles.assemblers;

import it.arteprogrammazione.restexample.commons.dto.ordersarticle.OrderArticleDTO;
import it.arteprogrammazione.restexample.commons.exceptions.commons.NotFoundException;
import it.arteprogrammazione.restexample.controllers.articles.ArticlesRestController;
import it.arteprogrammazione.restexample.controllers.orders.OrdersRestController;
import it.arteprogrammazione.restexample.controllers.ordersarticles.OrdersArticlesRestController;
import it.arteprogrammazione.restexample.repositories.articles.ArticleRepository;
import it.arteprogrammazione.restexample.repositories.common.entities.OrderArticle;
import it.arteprogrammazione.restexample.repositories.orders.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderArticleModelAssembler extends RepresentationModelAssemblerSupport<OrderArticle, OrderArticleDTO> {

    private final ArticleRepository articleRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public OrderArticleModelAssembler(ArticleRepository articleRepository,
                                      OrderRepository orderRepository) {
        super(OrdersArticlesRestController.class, OrderArticleDTO.class);
        this.articleRepository = articleRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderArticleDTO toModel(OrderArticle order) {
        OrderArticleDTO dto = new OrderArticleDTO();
        Integer id = order.getId();
        Integer idOrder = order.getIdOrder();
        Integer idArticle = order.getIdArticle();

        dto.setId(id);
        dto.setIdOrder(idOrder);
        dto.setIdArticle(idArticle);

        Link selfLink = linkTo(OrdersArticlesRestController.class).slash(id).withSelfRel();
        dto.add(selfLink);

        try {
            if (orderRepository.existsById(idOrder)) {
                Link paymentCardLink = linkTo(methodOn(OrdersRestController.class)
                        .findById(idOrder)).withRel("orders");
                dto.add(paymentCardLink);
            }

            if (articleRepository.existsById(idArticle)) {
                Link paymentCardLink = linkTo(methodOn(ArticlesRestController.class)
                        .findById(idArticle)).withRel("articles");
                dto.add(paymentCardLink);
            }

        } catch (NotFoundException ex) {
            //
        }
        return dto;
    }


}
