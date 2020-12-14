package it.arteprogrammazione.restexample.services.implementations.ordersarticles;

import it.arteprogrammazione.restexample.commons.dto.ordersarticle.RequestOrderArticleDTO;
import it.arteprogrammazione.restexample.commons.exceptions.commons.NotFoundException;
import it.arteprogrammazione.restexample.repositories.articles.ArticleRepository;
import it.arteprogrammazione.restexample.repositories.common.entities.Article;
import it.arteprogrammazione.restexample.repositories.common.entities.Order;
import it.arteprogrammazione.restexample.repositories.common.entities.OrderArticle;
import it.arteprogrammazione.restexample.repositories.orders.OrderRepository;
import it.arteprogrammazione.restexample.repositories.ordersarticles.OrderArticleRepository;
import it.arteprogrammazione.restexample.services.interfaces.ordersarticles.IOrderArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

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
    @Transactional
    public void hookArticleToOrder(RequestOrderArticleDTO request) throws NotFoundException {

        Integer idOrder = request.getIdOrder();
        Integer idArticle = request.getIdArticle();

        Order order = orderRepository.findById(idOrder).orElseThrow(
                () -> new NotFoundException("Order not exists"));

        Article article = articleRepository.findById(idArticle).orElseThrow(
                () -> new NotFoundException("Article not exists")
        );

        OrderArticle orderArticle = new OrderArticle();
        orderArticle.setIdOrder(idOrder);
        orderArticle.setIdArticle(idArticle);
        orderArticleRepository.save(orderArticle);

        orderRepository.setTotalPrice(order.getTotalPrice()+article.getPrice(), order.getId());
    }

    @Override
    @Transactional
    public void unhookArticleToOrder(Integer id) throws NotFoundException {

        if(!orderArticleRepository.existsById(id))
            throw new NotFoundException("Order Article not exists");

        OrderArticle orderArticle = orderArticleRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Order Article not exists"));

        Order order = orderRepository.findById(orderArticle.getIdOrder()).orElseThrow(
                () -> new NotFoundException("Order not exists"));

        Article article = articleRepository.findById(orderArticle.getIdArticle()).orElseThrow(
                () -> new NotFoundException("Article not exists")
        );



        orderArticleRepository.deleteById(orderArticle.getId());

        orderRepository.setTotalPrice(order.getTotalPrice()-article.getPrice(), order.getId());

    }
}
