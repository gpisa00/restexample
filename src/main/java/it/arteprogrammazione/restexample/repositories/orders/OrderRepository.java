package it.arteprogrammazione.restexample.repositories.orders;

import it.arteprogrammazione.restexample.repositories.common.entities.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {
}
