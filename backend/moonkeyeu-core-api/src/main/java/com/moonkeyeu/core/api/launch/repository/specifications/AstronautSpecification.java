package com.moonkeyeu.core.api.launch.repository.specifications;
import com.moonkeyeu.core.api.launch.model.astronaut.Astronaut;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

public class AstronautSpecification {
    public static Specification<Astronaut> hasAgency(String agencyId) {
        return (Root<Astronaut> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            Join<Object, Object> agency = root.join("agency", JoinType.LEFT);
            return builder.equal(agency.get("agencyId"), agencyId);
        };
    }
    public static Specification<Astronaut> hasStatus(String statusName) {
        return (Root<Astronaut> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            Join<Object, Object> astronautStatus = root.join("status", JoinType.LEFT);
            return builder.equal(astronautStatus.get("statusId"), statusName);
        };
    }
    public static Specification<Astronaut> hasNationality(String nationality) {
        return (Root<Astronaut> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            Join<Object, Object> astronautCountry = root.join("countries", JoinType.LEFT);
            return builder.equal(astronautCountry.get("countryId"), nationality);
        };
    }
    public static Specification<Astronaut> hasSearchKey(String key) {
        String searchKeyPattern = "%" + key.toLowerCase() + "%";
        return (Root<Astronaut> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            Predicate searchByAstronautName = builder.like(builder.lower(root.get("name")), searchKeyPattern);
            query.distinct(true);
            return builder.or(searchByAstronautName);
        };
    }
}
