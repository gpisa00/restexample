package it.arteprogrammazione.restexample.services.interfaces.articles;


import it.arteprogrammazione.restexample.commons.dto.articles.ArticleDTO;
import it.arteprogrammazione.restexample.commons.dto.articles.RequestArticleDTO;
import it.arteprogrammazione.restexample.commons.exceptions.customers.ConflictException;
import it.arteprogrammazione.restexample.commons.exceptions.customers.NotFoundException;
import org.springframework.hateoas.CollectionModel;

public interface IArticleService {

    ArticleDTO findById(Integer id) throws NotFoundException;

    CollectionModel<ArticleDTO> findAll() throws NotFoundException;

    ArticleDTO save(RequestArticleDTO request, boolean update) throws ConflictException;

    void deleteById(Integer id) throws NotFoundException;

}
