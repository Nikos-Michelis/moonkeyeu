package com.moonkeyeu.core.api.user.reporitory.specification;
import com.moonkeyeu.core.api.user.model.Contact;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class MessagesSpecification {
    public static Specification<Contact> hasSearchKey(String key) {
        String searchKeyPattern = "%" + key.toLowerCase() + "%";
        return (Root<Contact> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            Predicate searchByAstronautName = builder.like(builder.lower(root.get("email")), searchKeyPattern);
            query.distinct(true);
            return builder.or(searchByAstronautName);
        };
    }
}
