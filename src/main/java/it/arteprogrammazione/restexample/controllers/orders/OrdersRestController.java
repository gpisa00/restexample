package it.arteprogrammazione.restexample.controllers.orders;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.arteprogrammazione.restexample.commons.dto.orders.OrderDTO;
import it.arteprogrammazione.restexample.commons.dto.orders.RequestOrderDTO;
import it.arteprogrammazione.restexample.commons.exceptions.commons.ConflictException;
import it.arteprogrammazione.restexample.commons.exceptions.commons.NotFoundException;
import it.arteprogrammazione.restexample.services.interfaces.orders.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/orders")
public class OrdersRestController {

    private final IOrderService orderService;

    @Autowired
    public OrdersRestController(IOrderService orderService) {
        this.orderService = orderService;
    }

    @ApiOperation(code = 200, value = "find an order in the database by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "NOT FOUND"),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR"),
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<OrderDTO> findById(@PathVariable Integer id) throws NotFoundException {
        return ResponseEntity.ok(orderService.findById(id));
    }

    @ApiOperation(value = "find all orders in the database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "NOT FOUND"),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR"),
    })
    @GetMapping
    public ResponseEntity<CollectionModel<OrderDTO>> findAll() throws NotFoundException {
        return ResponseEntity.ok(orderService.findAll());
    }

    @ApiOperation(code = 201, value = "save order in the database")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "CREATED"),
            @ApiResponse(code = 400, message = "BAD_REQUEST"),
            @ApiResponse(code = 409, message = "CONFLICT"),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR"),
    })
    @PostMapping
    public ResponseEntity<OrderDTO> save(@Valid @RequestBody final RequestOrderDTO request) throws ConflictException, NotFoundException {
        return new ResponseEntity<>(orderService.save(request, false), HttpStatus.CREATED);
    }

    @ApiOperation(code = 200, value = "update order in the database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "NOT FOUND"),
            @ApiResponse(code = 409, message = "CONFLICT"),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR"),
    })
    @PutMapping
    public ResponseEntity<OrderDTO> update(@Valid @RequestBody final RequestOrderDTO request) throws NotFoundException, ConflictException {
        return ResponseEntity.ok(orderService.save(request, true));
    }

    @ApiOperation(code = 200, value = "delete an order in the database by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "NOT FOUND"),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR"),
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) throws NotFoundException{
        orderService.deleteById(id);
        return ResponseEntity.ok().build();
    }


}
