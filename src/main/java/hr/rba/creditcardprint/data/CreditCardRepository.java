package hr.rba.creditcardprint.data;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CreditCardRepository extends CrudRepository<CreditCard, Long>, JpaSpecificationExecutor {

    Optional<CreditCard> findCreditCardByOib(String oib);
}
