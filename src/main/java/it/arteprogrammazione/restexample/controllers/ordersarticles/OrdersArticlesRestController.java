package it.arteprogrammazione.restexample.controllers.ordersarticles;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.arteprogrammazione.restexample.commons.dto.ordersarticle.RequestOrderArticleDTO;
import it.arteprogrammazione.restexample.commons.exceptions.commons.ConflictException;
import it.arteprogrammazione.restexample.commons.exceptions.commons.NotFoundException;
import it.arteprogrammazione.restexample.services.interfaces.ordersarticles.IOrderArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/ordersarticles")
public class OrdersArticlesRestController {

    private final IOrderArticleService orderArticleService;

    @Autowired
    public OrdersArticlesRestController(IOrderArticleService orderArticleService) {
        this.orderArticleService = orderArticleService;
    }

    @ApiOperation(code = 201, value = "save order article in the database")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "CREATED"),
            @ApiResponse(code = 400, message = "BAD_REQUEST"),
            @ApiResponse(code = 409, message = "CONFLICT"),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR"),
    })
    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody final RequestOrderArticleDTO request) throws ConflictException, NotFoundException {
        orderArticleService.hookArticleToOrder(request);
        return ResponseEntity.created(null).build();
    }

    @ApiOperation(code = 200, value = "delete a order article in the database by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "NOT FOUND"),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR"),
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) throws NotFoundException {
        orderArticleService.unhookArticleToOrder(id);
        return ResponseEntity.ok().build();
    }
}
