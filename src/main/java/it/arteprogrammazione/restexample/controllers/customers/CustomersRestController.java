package it.arteprogrammazione.restexample.controllers.customers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.arteprogrammazione.restexample.commons.dto.CustomerDTO;
import it.arteprogrammazione.restexample.commons.dto.PaymentCardDTO;
import it.arteprogrammazione.restexample.commons.dto.RequestCustomerDTO;
import it.arteprogrammazione.restexample.commons.exceptions.customers.ConflictException;
import it.arteprogrammazione.restexample.commons.exceptions.customers.NotFoundException;
import it.arteprogrammazione.restexample.controllers.paymentcards.PaymentCardsRestController;
import it.arteprogrammazione.restexample.services.interfaces.customers.ICustomerService;
import it.arteprogrammazione.restexample.services.interfaces.paymentcards.IPaymentCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/customers")
public class CustomersRestController {

    private final ICustomerService customerService;
    private final IPaymentCardService paymentCardService;

    @Autowired
    public CustomersRestController(ICustomerService customerService,
                                   IPaymentCardService paymentCardService) {
        this.customerService = customerService;
        this.paymentCardService = paymentCardService;
    }

    //------------------ CREATE ---------------------------------------

    @ApiOperation(code = 201, value = "save customer in the database")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "CREATED"),
            @ApiResponse(code = 400, message = "BAD_REQUEST"),
            @ApiResponse(code = 409, message = "CONFLICT"),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR"),
    })
    @PostMapping
    public ResponseEntity<CustomerDTO> save(@Valid @RequestBody final RequestCustomerDTO request) throws ConflictException {
        return new ResponseEntity<>(customerService.save(request), HttpStatus.CREATED);
    }

    //------------------ READ ---------------------------------------

    @ApiOperation(code = 200, value = "find a customer in the database by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "NOT FOUND"),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR"),
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<CustomerDTO> findById(@PathVariable Integer id) throws NotFoundException {
        return ResponseEntity.ok(customerService.findById(id));
    }

    //------------------ UPDATE ---------------------------------------

    @ApiOperation(code = 200, value = "update customer in the database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "NOT FOUND"),
            @ApiResponse(code = 409, message = "CONFLICT"),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR"),
    })
    @PutMapping
    public ResponseEntity<CustomerDTO> save(@Valid @RequestBody final CustomerDTO request) throws NotFoundException {
        return ResponseEntity.ok(customerService.update(request));
    }

    //------------------ DELETE -------------------------------------------

    @ApiOperation(code = 200, value = "delete a customer in the database by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "NOT FOUND"),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR"),
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteById(@PathVariable Integer id) throws NotFoundException {
        customerService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    //------------------ READ ALL ---------------------------------------

    @ApiOperation(value = "find all customers in the database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "NOT FOUND"),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR"),
    })
    @GetMapping
    public ResponseEntity<CollectionModel<CustomerDTO>> findAllTest() throws NotFoundException {
        return ResponseEntity.ok(customerService.findAll());
    }

}
