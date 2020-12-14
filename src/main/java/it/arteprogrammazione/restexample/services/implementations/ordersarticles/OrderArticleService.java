package it.arteprogrammazione.restexample.services.implementations.ordersarticles;

import it.arteprogrammazione.restexample.commons.dto.ordersarticle.RequestOrderArticleDTO;
import it.arteprogrammazione.restexample.commons.exceptions.commons.NotFoundException;
import it.arteprogrammazione.restexample.repositories.articles.ArticleRepository;
import it.arteprogrammazione.restexample.repositories.common.entities.OrderArticle;
import it.arteprogrammazione.restexample.repositories.orders.OrderRepository;
import it.arteprogrammazione.restexample.repositories.ordersarticles.OrderArticleRepository;
import it.arteprogrammazione.restexample.services.interfaces.ordersarticles.IOrderArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderArticleService implements IOrderArticleService {

    private final OrderArticleRepository orderArticleRepository;
    private final OrderRepository orderRepository;
    private final ArticleRepository articleRepository;

    @Autowired
    public OrderArticleService(OrderArticleRepository orderArticleRepository, OrderRepository orderRepository, ArticleRepository articleRepository) {
        this.orderArticleRepository = orderArticleRepository;
        this.orderRepository = orderRepository;
        this.articleRepository = articleRepository;
    }

    @Override
    public void hookArticleToOrder(RequestOrderArticleDTO request) throws NotFoundException {

        Integer idOrder = request.getIdOrder();
        Integer idArticle = request.getIdArticle();

        if(!orderRepository.existsById(idOrder))
            throw new NotFoundException("Order not exists");

        if(!articleRepository.existsById(idArticle))
            throw new NotFoundException("Article not exists");

        OrderArticle orderArticle = new OrderArticle();
        orderArticle.setIdOrder(idOrder);
        orderArticle.setIdArticle(idArticle);

        orderArticleRepository.save(orderArticle);
    }

    @Override
    public void unhookArticleToOrder(Integer id) throws NotFoundException {

        if(!orderArticleRepository.existsById(id))
            throw new NotFoundException("Order Article not exists");

        orderArticleRepository.deleteById(id);
    }
}
