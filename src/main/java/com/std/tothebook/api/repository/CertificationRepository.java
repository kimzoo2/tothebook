package com.std.tothebook.api.repository;

import com.std.tothebook.api.entity.Certification;
import com.std.tothebook.api.enums.CertificationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CertificationRepository extends JpaRepository<Certification, Long> {

    void deleteAllByEmailAndType(String email, CertificationType type);

    Optional<Certification> findFirstByEmailAndTypeOrderByCreatedAtDesc(String email, CertificationType type);
}
