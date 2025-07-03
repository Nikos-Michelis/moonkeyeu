package com.moonkeyeu.core.api.launch.repository.specifications;

import com.moonkeyeu.core.api.launch.model.rocket.RocketConfiguration;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

public class RocketSpecification {

    public static Specification<RocketConfiguration> isActive(Boolean isActive){
        return (Root<RocketConfiguration> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            Join<Object, Object> rocketConfiguration = root.join("rocketConfiguration", JoinType.LEFT);
            return builder.equal(rocketConfiguration.get("active"), isActive);
        };
    }
    public static Specification<RocketConfiguration> isReusable(Boolean isReusable){
        return (Root<RocketConfiguration> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            Join<Object, Object> rocketConfiguration = root.join("rocketConfiguration", JoinType.LEFT);
            return builder.equal(rocketConfiguration.get("reusable"), isReusable);
        };
    }
    public static Specification<RocketConfiguration> hasAgency(String agencyId){
        return (Root<RocketConfiguration> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            Join<Object, Object> agency = root.join("agencies", JoinType.LEFT);
            return builder.equal(agency.get("agencyId"), agencyId);
        };
    }
    public static Specification<RocketConfiguration> hasSearchKey(String key) {
        String searchKeyPattern = "%" + key.toLowerCase() + "%";
        return (Root<RocketConfiguration> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            Predicate searchByAstronautName = builder.like(builder.lower(root.get("rocketName")), searchKeyPattern);
            query.distinct(true);
            return builder.or(searchByAstronautName);
        };
    }
}
