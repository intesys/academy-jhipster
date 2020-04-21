package it.intesys.academy.repository;

import it.intesys.academy.domain.Patient;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Patient entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Query("select p from Patient p where p.lastName like %:searchString% or p.firstName like %:searchString%")
    List<Patient> searchPatient(@Param("searchString") String searchString);
}
