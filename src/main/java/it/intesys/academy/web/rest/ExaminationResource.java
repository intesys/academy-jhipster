package it.intesys.academy.web.rest;

import it.intesys.academy.service.ExaminationService;
import it.intesys.academy.web.rest.errors.BadRequestAlertException;
import it.intesys.academy.service.dto.ExaminationDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link it.intesys.academy.domain.Examination}.
 */
@RestController
@RequestMapping("/api")
public class ExaminationResource {

    private final Logger log = LoggerFactory.getLogger(ExaminationResource.class);

    private static final String ENTITY_NAME = "examination";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExaminationService examinationService;

    public ExaminationResource(ExaminationService examinationService) {
        this.examinationService = examinationService;
    }

    /**
     * {@code POST  /examinations} : Create a new examination.
     *
     * @param examinationDTO the examinationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new examinationDTO, or with status {@code 400 (Bad Request)} if the examination has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/examinations")
    public ResponseEntity<ExaminationDTO> createExamination(@Valid @RequestBody ExaminationDTO examinationDTO) throws URISyntaxException {
        log.debug("REST request to save Examination : {}", examinationDTO);
        if (examinationDTO.getId() != null) {
            throw new BadRequestAlertException("A new examination cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExaminationDTO result = examinationService.save(examinationDTO);
        return ResponseEntity.created(new URI("/api/examinations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /examinations} : Updates an existing examination.
     *
     * @param examinationDTO the examinationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated examinationDTO,
     * or with status {@code 400 (Bad Request)} if the examinationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the examinationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/examinations")
    public ResponseEntity<ExaminationDTO> updateExamination(@Valid @RequestBody ExaminationDTO examinationDTO) throws URISyntaxException {
        log.debug("REST request to update Examination : {}", examinationDTO);
        if (examinationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ExaminationDTO result = examinationService.save(examinationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, examinationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /examinations} : get all the examinations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of examinations in body.
     */
    @GetMapping("/examinations")
    public ResponseEntity<List<ExaminationDTO>> getAllExaminations(Pageable pageable) {
        log.debug("REST request to get a page of Examinations");
        Page<ExaminationDTO> page = examinationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /examinations/:id} : get the "id" examination.
     *
     * @param id the id of the examinationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the examinationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/examinations/{id}")
    public ResponseEntity<ExaminationDTO> getExamination(@PathVariable Long id) {
        log.debug("REST request to get Examination : {}", id);
        Optional<ExaminationDTO> examinationDTO = examinationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(examinationDTO);
    }

    /**
     * {@code DELETE  /examinations/:id} : delete the "id" examination.
     *
     * @param id the id of the examinationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/examinations/{id}")
    public ResponseEntity<Void> deleteExamination(@PathVariable Long id) {
        log.debug("REST request to delete Examination : {}", id);
        examinationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
