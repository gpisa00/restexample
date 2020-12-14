package it.arteprogrammazione.restexample.services.implementations.articles.assemblers;

import it.arteprogrammazione.restexample.commons.dto.articles.ArticleDTO;
import it.arteprogrammazione.restexample.commons.dto.articles.RequestArticleDTO;
import it.arteprogrammazione.restexample.commons.exceptions.commons.NotFoundException;
import it.arteprogrammazione.restexample.controllers.articles.ArticlesRestController;
import it.arteprogrammazione.restexample.controllers.orders.OrdersRestController;
import it.arteprogrammazione.restexample.repositories.common.entities.Article;
import it.arteprogrammazione.restexample.repositories.common.entities.OrderArticle;
import it.arteprogrammazione.restexample.repositories.ordersarticles.OrderArticleRepository;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ArticleModelAssembler extends RepresentationModelAssemblerSupport<Article, ArticleDTO> {

    private final OrderArticleRepository orderArticleRepository;

    @Autowired
    public ArticleModelAssembler(OrderArticleRepository orderArticleRepository) {
        super(ArticlesRestController.class, ArticleDTO.class);
        this.orderArticleRepository = orderArticleRepository;
    }

    @Override
    public ArticleDTO toModel(Article article) {
        ArticleDTO dto = new ArticleDTO();
        Integer idArticle = article.getId();
        dto.setId(idArticle);
        dto.setDescription(article.getDescription());
        dto.setPrice(article.getPrice());
        Link selfLink = linkTo(ArticlesRestController.class).slash(idArticle).withSelfRel();
        dto.add(selfLink);
        try {
            Iterable<OrderArticle> orderArticles = orderArticleRepository.findByIdArticle(idArticle);
            if(!IterableUtils.isEmpty(orderArticles)){
                for(OrderArticle orderArticle : orderArticles){
                    Link orderLink = linkTo(methodOn(OrdersRestController.class)
                            .findById(orderArticle.getIdOrder())).withRel("orders");
                    dto.add(orderLink);
                }
            }

        } catch (NotFoundException ex) {
            //
        }
        return dto;
    }

    public Article toEntity(RequestArticleDTO request) {
        Article article = new Article();
        if(request.getId() != null)
            article.setId(request.getId());
        article.setDescription(request.getDescription());
        article.setPrice(request.getPrice());
        return article;
    }
}
