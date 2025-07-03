package com.moonkeyeu.core.api.launch.repository.specifications;

import com.moonkeyeu.core.api.launch.model.program.Programs;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class ProgramSpecification {
    public static Specification<Programs> hasSearchKey(String key) {
        String searchKeyPattern = "%" + key.toLowerCase() + "%";
        return (Root<Programs> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            Predicate searchByAstronautName = builder.like(builder.lower(root.get("name")), searchKeyPattern);
            query.distinct(true);
            return builder.or(searchByAstronautName);
        };
    }
}
