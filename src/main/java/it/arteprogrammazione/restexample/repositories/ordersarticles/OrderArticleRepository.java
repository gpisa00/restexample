package it.arteprogrammazione.restexample.repositories.ordersarticles;

import it.arteprogrammazione.restexample.repositories.common.entities.OrderArticle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderArticleRepository extends CrudRepository<OrderArticle, Integer> {
    Iterable<OrderArticle> findByIdArticle(Integer idArticle);

    void deleteByIdArticle(Integer id);

    Iterable<OrderArticle> findByIdOrder(Integer idOrder);

    void deleteByIdOrder(Integer id);
}
