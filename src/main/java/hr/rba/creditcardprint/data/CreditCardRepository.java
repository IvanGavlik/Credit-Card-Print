package hr.rba.creditcardprint.data;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CreditCardRepository extends CrudRepository<CreditCard, Long> {

    Optional<CreditCard> findCreditCardByOib(String oib);
}
