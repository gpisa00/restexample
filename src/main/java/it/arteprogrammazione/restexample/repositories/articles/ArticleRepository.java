package it.arteprogrammazione.restexample.repositories.articles;

import it.arteprogrammazione.restexample.repositories.common.entities.Article;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends CrudRepository<Article, Integer> {
}
