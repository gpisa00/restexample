package it.arteprogrammazione.restexample.repositories.customers;

import it.arteprogrammazione.restexample.repositories.common.entities.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {

    boolean existsByFirstNameAndLastName(String firstName, String lastName);

    //void deleteById(Integer id);

}
