package com.app.web.rest;

import com.app.domain.MedicinInfo;
import com.app.service.MedicinInfoService;
import com.app.web.rest.errors.BadRequestAlertException;

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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.app.domain.MedicinInfo}.
 */
@RestController
@RequestMapping("/api")
public class MedicinInfoResource {

    private final Logger log = LoggerFactory.getLogger(MedicinInfoResource.class);

    private static final String ENTITY_NAME = "medicinInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MedicinInfoService medicinInfoService;

    public MedicinInfoResource(MedicinInfoService medicinInfoService) {
        this.medicinInfoService = medicinInfoService;
    }

    /**
     * {@code POST  /medicin-infos} : Create a new medicinInfo.
     *
     * @param medicinInfo the medicinInfo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new medicinInfo, or with status {@code 400 (Bad Request)} if the medicinInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/medicin-infos")
    public ResponseEntity<MedicinInfo> createMedicinInfo(@RequestBody MedicinInfo medicinInfo) throws URISyntaxException {
        log.debug("REST request to save MedicinInfo : {}", medicinInfo);
        if (medicinInfo.getId() != null) {
            throw new BadRequestAlertException("A new medicinInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MedicinInfo result = medicinInfoService.save(medicinInfo);
        return ResponseEntity.created(new URI("/api/medicin-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /medicin-infos} : Updates an existing medicinInfo.
     *
     * @param medicinInfo the medicinInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated medicinInfo,
     * or with status {@code 400 (Bad Request)} if the medicinInfo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the medicinInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/medicin-infos")
    public ResponseEntity<MedicinInfo> updateMedicinInfo(@RequestBody MedicinInfo medicinInfo) throws URISyntaxException {
        log.debug("REST request to update MedicinInfo : {}", medicinInfo);
        if (medicinInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MedicinInfo result = medicinInfoService.save(medicinInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, medicinInfo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /medicin-infos} : get all the medicinInfos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of medicinInfos in body.
     */
    @GetMapping("/medicin-infos")
    public ResponseEntity<List<MedicinInfo>> getAllMedicinInfos(Pageable pageable) {
        log.debug("REST request to get a page of MedicinInfos");
        Page<MedicinInfo> page = medicinInfoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /medicin-infos/:id} : get the "id" medicinInfo.
     *
     * @param id the id of the medicinInfo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the medicinInfo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/medicin-infos/{id}")
    public ResponseEntity<MedicinInfo> getMedicinInfo(@PathVariable Long id) {
        log.debug("REST request to get MedicinInfo : {}", id);
        Optional<MedicinInfo> medicinInfo = medicinInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(medicinInfo);
    }

    /**
     * {@code DELETE  /medicin-infos/:id} : delete the "id" medicinInfo.
     *
     * @param id the id of the medicinInfo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/medicin-infos/{id}")
    public ResponseEntity<Void> deleteMedicinInfo(@PathVariable Long id) {
        log.debug("REST request to delete MedicinInfo : {}", id);
        medicinInfoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
