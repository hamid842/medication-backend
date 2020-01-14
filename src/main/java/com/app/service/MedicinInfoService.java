package com.app.service;

import com.app.domain.MedicinInfo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link MedicinInfo}.
 */
public interface MedicinInfoService {

    /**
     * Save a medicinInfo.
     *
     * @param medicinInfo the entity to save.
     * @return the persisted entity.
     */
    MedicinInfo save(MedicinInfo medicinInfo);

    /**
     * Get all the medicinInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MedicinInfo> findAll(Pageable pageable);


    /**
     * Get the "id" medicinInfo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MedicinInfo> findOne(Long id);

    /**
     * Delete the "id" medicinInfo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
