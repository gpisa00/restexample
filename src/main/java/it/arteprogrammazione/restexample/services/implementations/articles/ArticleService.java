package it.arteprogrammazione.restexample.services.implementations.articles;

import it.arteprogrammazione.restexample.commons.dto.articles.ArticleDTO;
import it.arteprogrammazione.restexample.commons.dto.articles.RequestArticleDTO;
import it.arteprogrammazione.restexample.commons.exceptions.commons.ConflictException;
import it.arteprogrammazione.restexample.commons.exceptions.commons.NotFoundException;
import it.arteprogrammazione.restexample.repositories.articles.ArticleRepository;
import it.arteprogrammazione.restexample.repositories.common.entities.Article;
import it.arteprogrammazione.restexample.repositories.common.entities.Order;
import it.arteprogrammazione.restexample.repositories.common.entities.OrderArticle;
import it.arteprogrammazione.restexample.repositories.orders.OrderRepository;
import it.arteprogrammazione.restexample.repositories.ordersarticles.OrderArticleRepository;
import it.arteprogrammazione.restexample.services.implementations.articles.assemblers.ArticleModelAssembler;
import it.arteprogrammazione.restexample.services.interfaces.articles.IArticleService;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ArticleService implements IArticleService {

    private final ArticleRepository articleRepository;
    private final OrderArticleRepository orderArticleRepository;
    private final OrderRepository orderRepository;

    private final ArticleModelAssembler articleModelAssembler;

    @Autowired
    public ArticleService(ArticleRepository articleRepository,
                          OrderArticleRepository orderArticleRepository,
                          OrderRepository orderRepository,
                          ArticleModelAssembler articleModelAssembler) {
        this.articleRepository = articleRepository;
        this.orderArticleRepository = orderArticleRepository;
        this.orderRepository = orderRepository;
        this.articleModelAssembler = articleModelAssembler;
    }

    @Override
    public ArticleDTO findById(Integer id) throws NotFoundException {
        Optional<Article> result = articleRepository.findById(id);
        if (result.isEmpty())
            throw new NotFoundException("Article " + id + " not found");
        return articleModelAssembler.toModel(result.get());
    }

    @Override
    public CollectionModel<ArticleDTO> findAll() throws NotFoundException {
        Iterable<Article> result = articleRepository.findAll();
        if (IterableUtils.isEmpty(result))
            throw new NotFoundException("Articles is empty");
        return articleModelAssembler.toCollectionModel(result);
    }

    @Override
    @Transactional
    public ArticleDTO save(RequestArticleDTO request, boolean update) throws ConflictException, NotFoundException {
        if(request.getPrice() != null)
            request.setPrice(Math.abs(request.getPrice()));

        if(!update){
            request.setId(null);
        }else{
            Integer idArticle = request.getId();

            Article article = articleRepository.findById(idArticle).orElseThrow(
                    () -> new NotFoundException("Article not exists")
            );

            updateTotalPriceOrders(idArticle, article.getPrice(), request.getPrice());

        }
        return articleModelAssembler.toModel(articleRepository.save(articleModelAssembler.toEntity(request)));
    }

    @Override
    @Transactional
    public void deleteById(Integer id) throws NotFoundException {
        if (!articleRepository.existsById(id))
            throw new NotFoundException("Article " + id + " not exixts");

        //Cancellazione tabelle correlate
        deleteOnTableRelation(id);
        //-------------------------------

        articleRepository.deleteById(id);
    }

    private void deleteOnTableRelation(Integer id) {

        if(!IterableUtils.isEmpty(orderArticleRepository.findByIdArticle(id)))
            orderArticleRepository.deleteByIdArticle(id);

    }

    private void updateTotalPriceOrders(Integer idArticle, Integer dbPrice, Integer requestPrice){
        if(requestPrice != null && !dbPrice.equals(requestPrice)){
            Iterable<OrderArticle> ordersarticles = orderArticleRepository.findByIdArticle(idArticle);
            for(OrderArticle orderArticle : ordersarticles){
                Order order = orderRepository.findById(orderArticle.getIdOrder()).get();
                orderRepository.setTotalPrice(
                        order.getTotalPrice() - dbPrice + requestPrice,
                        order.getId());
            }
        }
    }
}
