package com.std.tothebook.repository;

import com.std.tothebook.entity.Certification;

import java.util.Optional;

public interface CertificationCustomRepository {

    Optional<Certification> findForTemporaryPassword(String email);
}
