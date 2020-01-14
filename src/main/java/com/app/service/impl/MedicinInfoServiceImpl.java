package com.app.service.impl;

import com.app.service.MedicinInfoService;
import com.app.domain.MedicinInfo;
import com.app.repository.MedicinInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MedicinInfo}.
 */
@Service
@Transactional
public class MedicinInfoServiceImpl implements MedicinInfoService {

    private final Logger log = LoggerFactory.getLogger(MedicinInfoServiceImpl.class);

    private final MedicinInfoRepository medicinInfoRepository;

    public MedicinInfoServiceImpl(MedicinInfoRepository medicinInfoRepository) {
        this.medicinInfoRepository = medicinInfoRepository;
    }

    /**
     * Save a medicinInfo.
     *
     * @param medicinInfo the entity to save.
     * @return the persisted entity.
     */
    @Override
    public MedicinInfo save(MedicinInfo medicinInfo) {
        log.debug("Request to save MedicinInfo : {}", medicinInfo);
        return medicinInfoRepository.save(medicinInfo);
    }

    /**
     * Get all the medicinInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MedicinInfo> findAll(Pageable pageable) {
        log.debug("Request to get all MedicinInfos");
        return medicinInfoRepository.findAll(pageable);
    }


    /**
     * Get one medicinInfo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MedicinInfo> findOne(Long id) {
        log.debug("Request to get MedicinInfo : {}", id);
        return medicinInfoRepository.findById(id);
    }

    /**
     * Delete the medicinInfo by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MedicinInfo : {}", id);
        medicinInfoRepository.deleteById(id);
    }
}
