package com.moonkeyeu.core.api.user.reporitory;

import com.moonkeyeu.core.api.user.model.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long>, JpaSpecificationExecutor<Contact> {
    Page<Contact> findAll(Specification<Contact> specification, Pageable pageable);
    Optional<Contact> findByContactId(@Param("contactId") Long contactId);
}
