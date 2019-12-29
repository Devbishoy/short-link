package com.shortlink.app.service.impl;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.shortlink.app.domain.MapLink;
import com.shortlink.app.repository.MapLinkRepository;
import com.shortlink.app.service.DecodeService;
import com.shortlink.app.service.EncodeService;
import com.shortlink.app.service.MapLinkService;
import com.shortlink.app.service.dto.UrlDTO;
import com.shortlink.app.service.util.Messages;
import com.shortlink.app.service.util.URLUtil;

@Service
@Transactional
public class MapLinkServiceImpl implements MapLinkService {

	private final Logger log = LoggerFactory.getLogger(MapLinkServiceImpl.class);
	private final MapLinkRepository mapLinkRepository;

	@Autowired
	DecodeService decodeService;

	@Autowired
	EncodeService encodeService;

	public MapLinkServiceImpl(MapLinkRepository mapLinkRepository) {
		this.mapLinkRepository = mapLinkRepository;
	}

	@Override
	public MapLink save(MapLink mapLink) {
		return mapLinkRepository.save(mapLink);
	}

	@Override
	public MapLink findOne(Long id) {
		return mapLinkRepository.findById(id).orElse(null);
	}

	@Override
	public void delete(Long id) {
		mapLinkRepository.deleteById(id);
	}

	/**
	 * create short link and check if longurl or orignial url exist or not
	 * before
	 */
	@Override
	public String getShortLink(String url, String longUrl) {
		log.info("Shortening {}", longUrl);
		MapLink mapLink = mapLinkRepository.findByUrl(longUrl);
		if (mapLink == null) {
			mapLink = new MapLink();
			mapLink.setUrl(longUrl);
			mapLink.setOpened(0l);
			mapLink = mapLinkRepository.save(mapLink);

			String uniqueID = encodeService.encodeToBase62(mapLink.getId());
			mapLink.setKey(uniqueID);
			mapLinkRepository.save(mapLink);
		}

		String baseString = url;
		String shortenedURL = baseString + "/" + mapLink.getKey();
		return shortenedURL;
	}

	/**
	 * get orignialk url back using unique String
	 */
	@Override
	public String getLongURLFromID(String id) {
		Long Key = decodeService.decodeToBase10(id);
		MapLink mapLink = mapLinkRepository.findById(Key).orElse(null);

		if (mapLink == null)
			return null;
		else {
			log.info(Messages.UPDATE_OPEN_BEFORE_FOR, mapLink.getUrl());
			Long openBefore = mapLink.getOpened();
			openBefore = openBefore + 1;
			mapLink.setOpened(openBefore);
			mapLinkRepository.save(mapLink);
		}
		log.info(Messages.CONVERTING_SHORTENED_URL_BACK_TO, mapLink.getUrl());
		return mapLink.getUrl();
	}

	/**
	 * vailadate and check nullable of url
	 */
	@Override
	public boolean validateURL(String url) {
		if (StringUtils.isEmpty(url))
			return false;
		String longUrl = URLUtil.INSTANCE.trimString(url);
		return URLUtil.INSTANCE.validateURL(longUrl);
	}

	/**
	 * 
	 * return ResponseEntity result
	 */
	@Override
	public ResponseEntity<String> getShortLinkResponse(UrlDTO urlDTO, HttpServletRequest request) {
		String localURL = request.getRequestURL().toString();
		String longUrl = URLUtil.INSTANCE.trimString(urlDTO.getUrl());

		String shortUrl = "";
		try {
			shortUrl = getShortLink(localURL, longUrl);
		} catch (Exception ex) {
			log.error(Messages.ERORR_WHILE_GET_SHORT_LINK_FOR + longUrl, ex.getMessage());
			return new ResponseEntity<String>(shortUrl, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		log.info(Messages.SHORT_URL_CREATED + shortUrl);
		return new ResponseEntity<String>(shortUrl, HttpStatus.OK);
	}

	/**
	 * get RedirectView which will redirect to orgnial url
	 */
	@Override
	public RedirectView getRedirectURL(String id) {
		String redirectUrlString = Optional.ofNullable(getLongURLFromID(id)).orElse("");
		log.info(Messages.ORIGINAL_URL + redirectUrlString);
		RedirectView redirectView = new RedirectView();
		redirectUrlString = appeandHttp(redirectUrlString);
		redirectView.setUrl(redirectUrlString);
		return redirectView;
	}

	private String appeandHttp(String redirectUrlString) {
		if (!redirectUrlString.startsWith(Messages.HTTP) && !redirectUrlString.startsWith(Messages.HTTPS))
			redirectUrlString = Messages.HTTP + redirectUrlString;
		return redirectUrlString;
	}

	@Override
	public Page<MapLink> findAllURLS(Pageable page) {
		Page<MapLink> pageList = mapLinkRepository.findAll(page);
		return pageList;
	}

}
