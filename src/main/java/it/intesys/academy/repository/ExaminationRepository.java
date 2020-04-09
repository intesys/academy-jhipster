package it.intesys.academy.repository;

import it.intesys.academy.domain.Examination;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Examination entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExaminationRepository extends JpaRepository<Examination, Long> {

    @Query("select examination from Examination examination where examination.user.login = ?#{principal.username}")
    List<Examination> findByUserIsCurrentUser();
}
