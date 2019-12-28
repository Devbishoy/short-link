package com.shortlink.app.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.shortlink.app.service.EncodeService;

/**
 * EncodeService responsible for encode base10 to base62
 * 
 * @author Prog
 *
 */
@Service
public class EncodeServiceImpl implements EncodeService {

	private final Logger log = LoggerFactory.getLogger(DecodeServiceImpl.class);

	public EncodeServiceImpl() {
		initializeIndexToCharTable();
	}

	private static List<Character> indexToCharTable;

	private void initializeIndexToCharTable() {
		indexToCharTable = new ArrayList<>();
		for (int i = 0; i < 26; ++i) {
			char c = 'a';
			c += i;
			indexToCharTable.add(c);
		}
		for (int i = 26; i < 52; ++i) {
			char c = 'A';
			c += (i - 26);
			indexToCharTable.add(c);
		}
		for (int i = 52; i < 62; ++i) {
			char c = '0';
			c += (i - 52);
			indexToCharTable.add(c);
		}
	}

	@Override
	public String encodeToBase62(Long id) {
		List<Integer> base62ID = convertBase10ToBase62ID(id);
		StringBuilder uniqueURLID = new StringBuilder();
		for (int digit : base62ID) {
			uniqueURLID.append(indexToCharTable.get(digit));
		}
		log.info("Encode from " + id + " to ", uniqueURLID);
		return uniqueURLID.toString();
	}

	private List<Integer> convertBase10ToBase62ID(Long id) {
		List<Integer> digits = new LinkedList<>();
		while (id > 0) {
			int remainder = (int) (id % 62);
			((LinkedList<Integer>) digits).addFirst(remainder);
			id /= 62;
		}
		return digits;
	}

}
