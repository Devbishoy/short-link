package com.shortlink.app.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.shortlink.app.service.DecodeService;

/**
 * DecoderService responsible for decode from base62 to base10
 * 
 * @author Prog
 *
 */
@Service
public class DecodeServiceImpl implements DecodeService {

	private final Logger log = LoggerFactory.getLogger(DecodeServiceImpl.class);
	public DecodeServiceImpl() {
		initializeCharToIndexTable();
	}

	private static HashMap<Character, Integer> charToIndexTable;

	private void initializeCharToIndexTable() {
		charToIndexTable = new HashMap<>();
		for (int i = 0; i < 26; ++i) {
			char c = 'a';
			c += i;
			charToIndexTable.put(c, i);
		}
		for (int i = 26; i < 52; ++i) {
			char c = 'A';
			c += (i - 26);
			charToIndexTable.put(c, i);
		}
		for (int i = 52; i < 62; ++i) {
			char c = '0';
			c += (i - 52);
			charToIndexTable.put(c, i);
		}
	}

	@Override
	public Long decodeToBase10(String uniqueID) {
		List<Character> base62IDs = new ArrayList<>();
		for (int i = 0; i < uniqueID.length(); ++i) {
			base62IDs.add(uniqueID.charAt(i));
		}
		Long dictionaryKey = convertBase62ToBase10ID(base62IDs);
		log.info("Decode from " +uniqueID+ " to ", dictionaryKey);
		return dictionaryKey;
	}

	private Long convertBase62ToBase10ID(List<Character> ids) {
		long id = 0L;
		for (int i = 0, exp = ids.size() - 1; i < ids.size(); ++i, --exp) {
			int base10 = charToIndexTable.get(ids.get(i));
			id += (base10 * Math.pow(62.0, exp));
		}
		return id;
	}
}
