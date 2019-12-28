package com.shortlink.app.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

import com.shortlink.app.domain.MapLink;
import com.shortlink.app.service.dto.UrlDTO;

@Service
public interface MapLinkService {

	/**
	 * create new map link row
	 * 
	 * @param MapLink
	 * @return
	 */
	MapLink save(MapLink mapLink);

	/**
	 * find MapLink by id
	 * 
	 * @param id
	 * @return MapLink object
	 */
	MapLink findOne(Long id);

	/**
	 * delete MapLink by id
	 * 
	 * @param id
	 */
	void delete(Long id);

	/**
	 * 
	 * @param url
	 * @param url2
	 * @return
	 */
	String getShortLink(String url, String url2);

	/**
	 * 
	 * @param id
	 * @return
	 */
	String getLongURLFromID(String id);

	/**
	 * 
	 * @param url
	 * @return
	 */
	boolean validateURL(String url);

	/**
	 * 
	 * @param urlDTO
	 * @param request
	 * @return
	 */
	ResponseEntity<String> getShortLinkResponse(UrlDTO urlDTO, HttpServletRequest request);

	/**
	 * 
	 * @param id
	 * @return
	 */
	RedirectView getRedirectURL(String id);

	/**
	 * 
	 * @param page
	 * @return
	 */
	Page<MapLink> findAllURLS(Pageable page);

}
