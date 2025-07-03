package com.moonkeyeu.core.api.launch.repository.specifications;

import com.moonkeyeu.core.api.launch.model.launcher.Launcher;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

public class LauncherSpecification {

    public static Specification<Launcher> hasSearchKey(String key) {
        String searchKeyPattern = "%" + key.toLowerCase() + "%";
        return (Root<Launcher> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            Predicate searchByAstronautName = builder.like(builder.lower(root.get("serialNumber")), searchKeyPattern);
            query.distinct(true);
            return builder.or(searchByAstronautName);
        };
    }
}
