package com.shortlink.app.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shortlink.app.ShortLinkApp;


@SpringBootTest(classes = ShortLinkApp.class)
public class MapLinkServiceIT {

	@Autowired
	EncodeService encodeService;

	@Autowired
	DecodeService decodeService;

	@Test
	public void testEncodeSerivce() throws Exception {
		String str = encodeService.encodeToBase62(1001l);
		assertThat(str.equals("qj"));
	}

	@Test
	public void testDecodeSerivce() throws Exception {
		Long id = decodeService.decodeToBase10("qj");
		assertThat(id == 1001);
	}
}
