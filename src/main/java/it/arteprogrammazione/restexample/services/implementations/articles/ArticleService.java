package it.arteprogrammazione.restexample.services.implementations.articles;

import it.arteprogrammazione.restexample.commons.dto.articles.ArticleDTO;
import it.arteprogrammazione.restexample.commons.dto.articles.RequestArticleDTO;
import it.arteprogrammazione.restexample.commons.exceptions.customers.ConflictException;
import it.arteprogrammazione.restexample.commons.exceptions.customers.NotFoundException;
import it.arteprogrammazione.restexample.repositories.articles.ArticleRepository;
import it.arteprogrammazione.restexample.repositories.common.entities.Article;
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

    private final ArticleModelAssembler articleModelAssembler;

    @Autowired
    public ArticleService(ArticleRepository articleRepository,
                          OrderArticleRepository orderArticleRepository,
                          ArticleModelAssembler articleModelAssembler) {
        this.articleRepository = articleRepository;
        this.orderArticleRepository = orderArticleRepository;
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
    public ArticleDTO save(RequestArticleDTO request, boolean update) throws ConflictException {
        if(!update)
            request.setId(null);
        return articleModelAssembler.toModel(articleRepository.save(articleModelAssembler.toEntity(request)));
    }

    @Override
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
}
