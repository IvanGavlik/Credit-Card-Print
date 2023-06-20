package hr.rba.creditcardprint.search;

import hr.rba.creditcardprint.data.CreditCard;
import hr.rba.creditcardprint.data.CreditCardRepository;
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



@Service
public class SearchPrintServiceImpl implements SearchPrintService {

    private CreditCardRepository repo;

    private SearchPrintMapper mapper;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    public SearchPrintServiceImpl(SearchPrintMapper searchPrintMapper) {
        this.mapper = searchPrintMapper;
    }
    @Override
    public List<CreditCardPrintDetailsDto> search(String fName, String lName, String oib, String status) {
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
