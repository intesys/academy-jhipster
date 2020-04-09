package it.intesys.academy.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link it.intesys.academy.domain.Examination} entity.
 */
public class ExaminationDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Integer weight;

    @NotNull
    private Integer height;

    @NotNull
    private Integer diastolicPressure;

    @NotNull
    private Integer systolicPressure;

    private ZonedDateTime examinationDate;

    private ZonedDateTime lastModifiedDate;

    private ZonedDateTime createdDate;


    private Long userId;

    private String userUserName;

    private Long patientId;

    private String patientLastName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getDiastolicPressure() {
        return diastolicPressure;
    }

    public void setDiastolicPressure(Integer diastolicPressure) {
        this.diastolicPressure = diastolicPressure;
    }

    public Integer getSystolicPressure() {
        return systolicPressure;
    }

    public void setSystolicPressure(Integer systolicPressure) {
        this.systolicPressure = systolicPressure;
    }

    public ZonedDateTime getExaminationDate() {
        return examinationDate;
    }

    public void setExaminationDate(ZonedDateTime examinationDate) {
        this.examinationDate = examinationDate;
    }

    public ZonedDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserUserName() {
        return userUserName;
    }

    public void setUserUserName(String userUserName) {
        this.userUserName = userUserName;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getPatientLastName() {
        return patientLastName;
    }

    public void setPatientLastName(String patientLastName) {
        this.patientLastName = patientLastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ExaminationDTO examinationDTO = (ExaminationDTO) o;
        if (examinationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), examinationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExaminationDTO{" +
            "id=" + getId() +
            ", weight=" + getWeight() +
            ", height=" + getHeight() +
            ", diastolicPressure=" + getDiastolicPressure() +
            ", systolicPressure=" + getSystolicPressure() +
            ", examinationDate='" + getExaminationDate() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", userId=" + getUserId() +
            ", userUserName='" + getUserUserName() + "'" +
            ", patientId=" + getPatientId() +
            ", patientLastName='" + getPatientLastName() + "'" +
            "}";
    }
}
