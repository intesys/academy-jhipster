package it.intesys.academy.web.rest.api;

import it.intesys.academy.domain.Examination;
import it.intesys.academy.repository.ExaminationRepository;
import it.intesys.academy.service.mapper.PatientMapper;
import it.intesys.academy.web.api.ExaminationsApiDelegate;
import it.intesys.academy.web.api.model.ExaminationApiDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExaminationApiDelegateImpl implements ExaminationsApiDelegate {

    private final ExaminationRepository examinationRepository;
    private final PatientMapper patientMapper;

    public ExaminationApiDelegateImpl(ExaminationRepository examinationRepository, PatientMapper patientMapper) {
        this.examinationRepository = examinationRepository;
        this.patientMapper = patientMapper;
    }

    @Override
    public ResponseEntity<ExaminationApiDTO> saveExamination(ExaminationApiDTO examinationApiDTO) {
        Examination examination = toEntity(examinationApiDTO);
        examination.setCreatedDate(ZonedDateTime.now());
        examination.setLastModifiedDate(ZonedDateTime.now());
        examinationRepository.save(examination);
        return ResponseEntity.ok(toDto(examination));
    }

    @Override
    public ResponseEntity<List<ExaminationApiDTO>> getPatientExaminations(Long patientId) {
        List<ExaminationApiDTO> examinations = examinationRepository.findAllByPatientId(patientId).stream()
            .map(examination -> toDto(examination))
            .collect(Collectors.toList());
        return ResponseEntity.ok(examinations);
    }

    private ExaminationApiDTO toDto(Examination examination) {
        ExaminationApiDTO examinationApiDTO = new ExaminationApiDTO();
        examinationApiDTO.setId(examination.getId());
        examinationApiDTO.setPatientId(examination.getPatient().getId());
        examinationApiDTO.setExaminationDate(examination.getExaminationDate().toOffsetDateTime());
        examinationApiDTO.setDiastolicPressure(examination.getDiastolicPressure());
        examinationApiDTO.setHeight(examination.getHeight());
        examinationApiDTO.setSystolicPressure(examination.getSystolicPressure());
        examinationApiDTO.setWeight(examination.getWeight());
        return examinationApiDTO;
    }

    private Examination toEntity(ExaminationApiDTO examinationDTO) {
        Examination examination = new Examination();
        examination.setPatient(patientMapper.fromId(examinationDTO.getPatientId()));
        examination.setExaminationDate(examinationDTO.getExaminationDate().toZonedDateTime());
        examination.setDiastolicPressure(examinationDTO.getDiastolicPressure());
        examination.setHeight(examinationDTO.getHeight());
        examination.setSystolicPressure(examinationDTO.getSystolicPressure());
        examination.setWeight(examinationDTO.getWeight());
        return examination;
    }


}
