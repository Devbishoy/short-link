package com.shortlink.app.web.rest;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.shortlink.app.domain.MapLink;
import com.shortlink.app.service.MapLinkService;
import com.shortlink.app.service.dto.UrlDTO;

import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.ApiParam;

/**
 * 
 * @author Prog Controller to get short link
 */
@RestController
@RequestMapping("/api")
public class ShortLinkResource {

	private final Logger log = LoggerFactory.getLogger(ShortLinkResource.class);

	private final MapLinkService mapLinkService;

	public ShortLinkResource(MapLinkService mapLinkService) {
		this.mapLinkService = mapLinkService;
	}

	/**
	 * 
	 * @param id
	 *            is unique string identify short link
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 * @throws Exception
	 */
	@GetMapping("/link/{id}")
	@Timed
	public RedirectView getOrignalURL(@PathVariable String id, HttpServletRequest request, HttpServletResponse response)
			throws IOException, URISyntaxException, Exception {
		log.info("Received shortened url to redirect: " + id);
		// TODO redirect to Error page in case of exception
		return mapLinkService.getRedirectURL(id);
	}

	/**
	 * 
	 * @param urlDTO
	 *            JSON contain URL and value is orignal URL or long URL
	 * @param request
	 * @return
	 */
	@PostMapping("/link")
	@Timed
	public ResponseEntity<String> creatShortLink(@RequestBody UrlDTO urlDTO, HttpServletRequest request) {
		log.info("Received url to shorten: " + urlDTO.getUrl());
		boolean vaildCheck = mapLinkService.validateURL(urlDTO.getUrl());
		if (vaildCheck == false)
			return new ResponseEntity<String>("please Enter VailURL", HttpStatus.BAD_REQUEST);

		return mapLinkService.getShortLinkResponse(urlDTO, request);

	}

	/**
	 * 
	 * @param pageable
	 * @return data pagenation
	 * @apiParam : take paramters page and size as url paramter ex:?page=0& in
	 *           case of no parameter default value is size 20 and page 0
	 */
	@GetMapping("/links")
	@Timed
	public Page<MapLink> getAllURLS(@ApiParam Pageable pageable) {
		return mapLinkService.findAllURLS(pageable);
	}

}
