package it.arteprogrammazione.restexample.controllers.customers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.arteprogrammazione.restexample.commons.dto.CustomerDTO;
import it.arteprogrammazione.restexample.commons.dto.RequestCustomerDTO;
import it.arteprogrammazione.restexample.commons.exceptions.customers.ConflictException;
import it.arteprogrammazione.restexample.commons.exceptions.customers.NotFoundException;
import it.arteprogrammazione.restexample.services.interfaces.customers.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CustomersRestController {

    private final ICustomerService customerService;

    @Autowired
    public CustomersRestController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    //------------------ CREATE ---------------------------------------

    @ApiOperation(code = 201, value = "save customer in the database")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "CREATED"),
            @ApiResponse(code = 400, message = "BAD_REQUEST"),
            @ApiResponse(code = 409, message = "CONFLICT"),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR"),
    })
    @PostMapping(value = "/customers")
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
    @GetMapping(value = "/customers/{id}")
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
    @PutMapping(value = "/customers")
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
    @DeleteMapping(value = "/customers/{id}")
    public ResponseEntity deleteById(@PathVariable Integer id) throws NotFoundException {
        customerService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    //------------------ READ ALL ---------------------------------------

    @ApiOperation(code = 200, value = "find a customer in the database by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "NOT FOUND"),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR"),
    })
    @GetMapping(value = "/customers")
    public ResponseEntity<List<CustomerDTO>> findAll() throws NotFoundException {
        return ResponseEntity.ok(customerService.findAll());
    }

    @ApiOperation(code = 200, value = "find a customer in the database by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "NOT FOUND"),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR"),
    })
    @GetMapping(value = "/test/customers")
    public ResponseEntity<List<EntityModel<CustomerDTO>>> findAllTest() throws NotFoundException {
        List<EntityModel<CustomerDTO>> list = new ArrayList<>();
        customerService.findAll().stream().forEach(customerDTO -> list.add(EntityModel.of(customerDTO)));
        return ResponseEntity.ok(list);
    }


}
