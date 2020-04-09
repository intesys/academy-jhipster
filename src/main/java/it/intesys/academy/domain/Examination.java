package it.intesys.academy.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.ZonedDateTime;

/**
 * A Examination.
 */
@Entity
@Table(name = "examination")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Examination implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "weight", nullable = false)
    private Integer weight;

    @NotNull
    @Column(name = "height", nullable = false)
    private Integer height;

    @NotNull
    @Column(name = "diastolic_pressure", nullable = false)
    private Integer diastolicPressure;

    @NotNull
    @Column(name = "systolic_pressure", nullable = false)
    private Integer systolicPressure;

    @Column(name = "examination_date")
    private ZonedDateTime examinationDate;

    @Column(name = "last_modified_date")
    private ZonedDateTime lastModifiedDate;

    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    @ManyToOne
    @JsonIgnoreProperties("examinations")
    private User user;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("examinations")
    private Patient patient;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getWeight() {
        return weight;
    }

    public Examination weight(Integer weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getHeight() {
        return height;
    }

    public Examination height(Integer height) {
        this.height = height;
        return this;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getDiastolicPressure() {
        return diastolicPressure;
    }

    public Examination diastolicPressure(Integer diastolicPressure) {
        this.diastolicPressure = diastolicPressure;
        return this;
    }

    public void setDiastolicPressure(Integer diastolicPressure) {
        this.diastolicPressure = diastolicPressure;
    }

    public Integer getSystolicPressure() {
        return systolicPressure;
    }

    public Examination systolicPressure(Integer systolicPressure) {
        this.systolicPressure = systolicPressure;
        return this;
    }

    public void setSystolicPressure(Integer systolicPressure) {
        this.systolicPressure = systolicPressure;
    }

    public ZonedDateTime getExaminationDate() {
        return examinationDate;
    }

    public Examination examinationDate(ZonedDateTime examinationDate) {
        this.examinationDate = examinationDate;
        return this;
    }

    public void setExaminationDate(ZonedDateTime examinationDate) {
        this.examinationDate = examinationDate;
    }

    public ZonedDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public Examination lastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public Examination createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public User getUser() {
        return user;
    }

    public Examination user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Patient getPatient() {
        return patient;
    }

    public Examination patient(Patient patient) {
        this.patient = patient;
        return this;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Examination)) {
            return false;
        }
        return id != null && id.equals(((Examination) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Examination{" +
            "id=" + getId() +
            ", weight=" + getWeight() +
            ", height=" + getHeight() +
            ", diastolicPressure=" + getDiastolicPressure() +
            ", systolicPressure=" + getSystolicPressure() +
            ", examinationDate='" + getExaminationDate() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
