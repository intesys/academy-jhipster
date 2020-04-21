package it.intesys.academy.web.rest.api;

import it.intesys.academy.domain.Patient;
import it.intesys.academy.repository.PatientRepository;
import it.intesys.academy.web.api.PatientApiDelegate;
import it.intesys.academy.web.api.model.CounterApiDTO;
import it.intesys.academy.web.api.model.PatientApiDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientApiDelegateImpl implements PatientApiDelegate {

    private final PatientRepository patientRepository;

    public PatientApiDelegateImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public ResponseEntity<CounterApiDTO> countPatients() {
        Long count = patientRepository.count();
        CounterApiDTO counterApiDTO = new CounterApiDTO();
        counterApiDTO.setCount(count.intValue());
        return ResponseEntity.ok(counterApiDTO);
    }

    @Override
    public ResponseEntity<PatientApiDTO> getPatient(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
            .orElseThrow(IllegalArgumentException::new);
        PatientApiDTO patientApiDTO = toDto(patient);
        return ResponseEntity.ok(patientApiDTO);
    }

    private PatientApiDTO toDto(Patient patient) {
        return new PatientApiDTO()
            .id(patient.getId())
            .firstName(patient.getFirstName())
            .lastName(patient.getLastName())
            .birthDate(patient.getBirthDate())
            .fiscalCode(patient.getFiscalCode());
    }

    @Override
    public ResponseEntity<List<PatientApiDTO>> searchPatient(String search) {
        List<PatientApiDTO> patients = patientRepository.searchPatient(search)
            .stream()
            .map(this::toDto)
            .collect(Collectors.toList());
        return ResponseEntity.ok(patients);
    }
}
