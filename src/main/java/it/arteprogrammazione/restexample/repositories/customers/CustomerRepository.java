package it.arteprogrammazione.restexample.repositories.customers;

import it.arteprogrammazione.restexample.repositories.customers.entities.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {

    boolean existsByFirstNameAndLastName(String firstName, String LastName);

    //void deleteById(Integer id);

}
