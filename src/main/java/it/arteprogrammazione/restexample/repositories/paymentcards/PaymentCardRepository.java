package it.arteprogrammazione.restexample.repositories.paymentcards;

import it.arteprogrammazione.restexample.repositories.common.entities.PaymentCard;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentCardRepository extends CrudRepository<PaymentCard, Integer> {

    Optional<PaymentCard> findByIdCustomer(Integer idCustomer);
}
