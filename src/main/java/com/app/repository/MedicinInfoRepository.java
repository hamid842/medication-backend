package com.app.repository;

import com.app.domain.MedicinInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MedicinInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedicinInfoRepository extends JpaRepository<MedicinInfo, Long> {

}
