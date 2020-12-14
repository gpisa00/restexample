package it.arteprogrammazione.restexample.controllers.paymentcards;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.arteprogrammazione.restexample.commons.dto.paymentcards.PaymentCardDTO;
import it.arteprogrammazione.restexample.commons.dto.paymentcards.RequestPaymentCardDTO;
import it.arteprogrammazione.restexample.commons.exceptions.commons.ConflictException;
import it.arteprogrammazione.restexample.commons.exceptions.commons.NotFoundException;
import it.arteprogrammazione.restexample.services.interfaces.paymentcards.IPaymentCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/paymentcards")
public class PaymentCardsRestController {

    private final IPaymentCardService paymentCardService;

    @Autowired
    public PaymentCardsRestController(IPaymentCardService paymentCardService) {
        this.paymentCardService = paymentCardService;
    }

    @ApiOperation(code = 200, value = "find a payment card in the database by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "NOT FOUND"),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR"),
    })
    @GetMapping(value = "/{idCustomer}")
    public ResponseEntity<PaymentCardDTO> findById(@PathVariable Integer idCustomer) throws NotFoundException {
        return ResponseEntity.ok(paymentCardService.findById(idCustomer));
    }

    @ApiOperation(value = "find all payment cards in the database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "NOT FOUND"),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR"),
    })
    @GetMapping
    public ResponseEntity<CollectionModel<PaymentCardDTO>> findAll() throws NotFoundException {
        return ResponseEntity.ok(paymentCardService.findAll());
    }

    @ApiOperation(code = 201, value = "save payment card in the database")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "CREATED"),
            @ApiResponse(code = 400, message = "BAD_REQUEST"),
            @ApiResponse(code = 409, message = "CONFLICT"),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR"),
    })
    @PostMapping
    public ResponseEntity<PaymentCardDTO> save(@Valid @RequestBody final RequestPaymentCardDTO request) throws ConflictException, NotFoundException {
        return new ResponseEntity<>(paymentCardService.save(request), HttpStatus.CREATED);
    }

    @ApiOperation(code = 200, value = "delete a payment card in the database by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "NOT FOUND"),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR"),
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) throws NotFoundException{
        paymentCardService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(code = 200, value = "update payment card in the database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "NOT FOUND"),
            @ApiResponse(code = 409, message = "CONFLICT"),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR"),
    })
    @PutMapping
    public ResponseEntity<PaymentCardDTO> update(@Valid @RequestBody final RequestPaymentCardDTO request) throws NotFoundException, ConflictException {
        return ResponseEntity.ok(paymentCardService.update(request));
    }
}
