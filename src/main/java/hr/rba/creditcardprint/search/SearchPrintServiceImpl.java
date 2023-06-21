package hr.rba.creditcardprint.search;

import hr.rba.creditcardprint.data.CreditCard;
import hr.rba.creditcardprint.openapi.model.CreditCardPrintDetailsDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * The implementation of the SearchPrintService interface that provides
 * functionality to search for credit card print details
 * based on various search criteria.
 */
@Service
public class SearchPrintServiceImpl implements SearchPrintService {

    private final SearchPrintMapper mapper;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Constructs a SearchPrintServiceImpl with the specified SearchPrintMapper.
     *
     * @param searchPrintMapper The mapper used for mapping entities to DTOs.
     */
    @Autowired
    public SearchPrintServiceImpl(final SearchPrintMapper searchPrintMapper) {
        this.mapper = searchPrintMapper;
    }

    /**
     * Searches for credit card print details based on params.
     *
     * @param fName
     * @param lName
     * @param oib
     * @param status
     * @return A list of CreditCardPrintDetailsDto objects that match the search criteria.
     */
    @Override
    public List<CreditCardPrintDetailsDto> search(final String fName,
                                                  final String lName,
                                                  final String oib,
                                                  final String status) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CreditCard> criteriaQuery = criteriaBuilder.createQuery(CreditCard.class);
        Root<CreditCard> itemRoot = criteriaQuery.from(CreditCard.class);

        List<Predicate> predicates = new ArrayList<>();

        Predicate firstNamePredicate = null;
        if (!Objects.isNull(fName) && !fName.isEmpty()) {
           firstNamePredicate = criteriaBuilder.equal(itemRoot.get("firstName"), fName);
        }
        predicates.add(firstNamePredicate);

        Predicate lastNamePredicate = null;
        if (!Objects.isNull(lName) && !lName.isEmpty()) {
            lastNamePredicate = criteriaBuilder.equal(itemRoot.get("lastName"), lName);
        }
        predicates.add(lastNamePredicate);


        Predicate oibPredicate = null;
        if (!Objects.isNull(oib) && !oib.isEmpty()) {
            oibPredicate = criteriaBuilder.equal(itemRoot.get("oib"), oib);
        }
        predicates.add(oibPredicate);


        Predicate statusPredicate = null;
        if (!Objects.isNull(status) && !status.isEmpty()) {
            statusPredicate = criteriaBuilder.equal(itemRoot.get("status"), status);
        }
        predicates.add(statusPredicate);


        Predicate[] predicatesArray = predicates.stream()
                .filter(el -> el != null)
                .toArray(Predicate[]::new);

        if (predicatesArray != null && predicatesArray.length > 0) {
            Predicate finalPredicate  = criteriaBuilder.and(predicatesArray);
            criteriaQuery.where(finalPredicate);
        }

        return this.mapper.entitiesToDtos(
                entityManager.createQuery(criteriaQuery)
                        .getResultList()
        );
    }
}
