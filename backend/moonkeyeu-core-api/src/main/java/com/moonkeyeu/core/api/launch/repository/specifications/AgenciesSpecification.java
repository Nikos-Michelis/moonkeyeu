package com.moonkeyeu.core.api.launch.repository.specifications;

import com.moonkeyeu.core.api.launch.model.agency.Agencies;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

public class AgenciesSpecification {
    public static Specification<Agencies> hasSearchKey(String key) {
        String searchKeyPattern = "%" + key.toLowerCase() + "%";
        return (Root<Agencies> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            Predicate searchByAstronautName = builder.like(builder.lower(root.get("agencyName")), searchKeyPattern);
            query.distinct(true);
            return builder.or(searchByAstronautName);
        };
    }
    public static Specification<Agencies> isFeatured() {
        return (root, query, builder) -> builder.isTrue(root.get("featured"));
    }
}
