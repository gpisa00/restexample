package it.arteprogrammazione.restexample.controllers.articles;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.arteprogrammazione.restexample.commons.dto.articles.ArticleDTO;
import it.arteprogrammazione.restexample.commons.dto.articles.RequestArticleDTO;
import it.arteprogrammazione.restexample.commons.exceptions.customers.ConflictException;
import it.arteprogrammazione.restexample.commons.exceptions.customers.NotFoundException;
import it.arteprogrammazione.restexample.services.interfaces.articles.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/articles")
public class ArticlesRestController {

    private final IArticleService articleService;

    @Autowired
    public ArticlesRestController(IArticleService articleService) {
        this.articleService = articleService;
    }

    @ApiOperation(code = 200, value = "find an article in the database by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "NOT FOUND"),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR"),
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<ArticleDTO> findById(@PathVariable Integer id) throws NotFoundException {
        return ResponseEntity.ok(articleService.findById(id));
    }

    @ApiOperation(value = "find all articles in the database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "NOT FOUND"),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR"),
    })
    @GetMapping
    public ResponseEntity<CollectionModel<ArticleDTO>> findAll() throws NotFoundException {
        return ResponseEntity.ok(articleService.findAll());
    }

    @ApiOperation(code = 201, value = "save article in the database")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "CREATED"),
            @ApiResponse(code = 400, message = "BAD_REQUEST"),
            @ApiResponse(code = 409, message = "CONFLICT"),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR"),
    })
    @PostMapping
    public ResponseEntity<ArticleDTO> save(@Valid @RequestBody final RequestArticleDTO request) throws ConflictException {
        return new ResponseEntity<>(articleService.save(request, false), HttpStatus.CREATED);
    }

    @ApiOperation(code = 200, value = "update article in the database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "NOT FOUND"),
            @ApiResponse(code = 409, message = "CONFLICT"),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR"),
    })
    @PutMapping
    public ResponseEntity<ArticleDTO> update(@Valid @RequestBody final RequestArticleDTO request) throws NotFoundException, ConflictException {
        return ResponseEntity.ok(articleService.save(request, true));
    }

    @ApiOperation(code = 200, value = "delete an article in the database by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "NOT FOUND"),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR"),
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) throws NotFoundException{
        articleService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
