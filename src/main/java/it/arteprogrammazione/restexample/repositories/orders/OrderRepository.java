package it.arteprogrammazione.restexample.repositories.orders;

import it.arteprogrammazione.restexample.repositories.common.entities.Order;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {

    @Modifying
    @Query("update Order o set o.totalPrice = ?1 where o.id = ?2")
    int setTotalPrice(Integer totalPrice, Integer id);

}
