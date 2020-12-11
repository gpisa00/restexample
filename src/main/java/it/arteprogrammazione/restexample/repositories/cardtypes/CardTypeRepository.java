package it.arteprogrammazione.restexample.repositories.cardtypes;

import it.arteprogrammazione.restexample.repositories.common.entities.CardType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardTypeRepository extends CrudRepository<CardType, Integer> {
}
