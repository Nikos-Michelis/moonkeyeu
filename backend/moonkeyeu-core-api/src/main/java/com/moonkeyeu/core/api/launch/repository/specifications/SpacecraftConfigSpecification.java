package com.moonkeyeu.core.api.launch.repository.specifications;

import com.moonkeyeu.core.api.launch.model.spacecraft.SpacecraftConfiguration;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class SpacecraftConfigSpecification {
    public static Specification<SpacecraftConfiguration> hasSearchKey(String key) {
        String searchKeyPattern = "%" + key.toLowerCase() + "%";
        return (Root<SpacecraftConfiguration> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            Predicate searchByAstronautName = builder.like(builder.lower(root.get("spacecraftConfName")), searchKeyPattern);
            query.distinct(true);
            return builder.or(searchByAstronautName);
        };
    }
}
