package it.intesys.academy.service.mapper;


import it.intesys.academy.domain.*;
import it.intesys.academy.service.dto.ExaminationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Examination} and its DTO {@link ExaminationDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, PatientMapper.class})
public interface ExaminationMapper extends EntityMapper<ExaminationDTO, Examination> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userUserName")
    @Mapping(source = "patient.id", target = "patientId")
    @Mapping(source = "patient.lastName", target = "patientLastName")
    ExaminationDTO toDto(Examination examination);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "patientId", target = "patient")
    Examination toEntity(ExaminationDTO examinationDTO);

    default Examination fromId(Long id) {
        if (id == null) {
            return null;
        }
        Examination examination = new Examination();
        examination.setId(id);
        return examination;
    }
}
