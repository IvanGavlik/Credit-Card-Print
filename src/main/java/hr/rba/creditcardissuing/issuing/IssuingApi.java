package hr.rba.creditcardissuing.issuing;

import hr.rba.creditcardissuing.openapi.api.CreditCardApiDelegate;
import hr.rba.creditcardissuing.openapi.model.IssuingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public final class IssuingApi implements CreditCardApiDelegate {

    private final IssuingService service;

    /**
     * IssuingApi constructor.
     *
     * @param issuingService
     */
    @Autowired
    public IssuingApi(final IssuingService issuingService) {
        this.service = issuingService;
    }

    /**
     * Issue credit card.
     *
     * @return issued credit card.
     */
    public ResponseEntity<IssuingDto> issueCreditCard() {
        IssuingDto i = new IssuingDto();
        i.setName("test");
        return ResponseEntity.of(Optional.of(i));
    }
}
