package it.arteprogrammazione.restexample.controllers.paymentcards;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.arteprogrammazione.restexample.commons.dto.PaymentCardDTO;
import it.arteprogrammazione.restexample.commons.exceptions.customers.NotFoundException;
import it.arteprogrammazione.restexample.services.interfaces.paymentcards.IPaymentCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
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
    @GetMapping(value = "/paymentcards/{idCustomer}")
    public ResponseEntity<PaymentCardDTO> findById(@PathVariable Integer idCustomer) throws NotFoundException {
        return ResponseEntity.ok(paymentCardService.findById(idCustomer));
    }
}
