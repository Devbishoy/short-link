package com.shortlink.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shortlink.app.domain.Authority;
import com.shortlink.app.domain.MapLink;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
@Repository
public interface MapLinkRepository extends JpaRepository<MapLink, Long> {

	/**
	 * 
	 * @param url
	 *            find if url exist before or not
	 * @return
	 */
	MapLink findByUrl(@Param("url") String url);

	/**
	 * get all data as pages using Pageable paramters
	 */
	Page<MapLink> findAll(Pageable page);
}
