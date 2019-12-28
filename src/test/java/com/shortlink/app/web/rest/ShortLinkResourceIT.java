package com.shortlink.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.shortlink.app.ShortLinkApp;
import com.shortlink.app.repository.MapLinkRepository;
import com.shortlink.app.service.MapLinkService;
import com.shortlink.app.service.dto.UrlDTO;
import com.shortlink.app.web.rest.errors.ExceptionTranslator;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShortLinkApp.class)
public class ShortLinkResourceIT {

	private MockMvc mockMvc;

	@Autowired
	private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

	@Autowired
	private ExceptionTranslator exceptionTranslator;

	@Autowired
	private MappingJackson2HttpMessageConverter jacksonMessageConverter;
	
	@Autowired
	MapLinkRepository mapLinkRepository;
	
	@Autowired
	MapLinkService mapLinkService;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(new ShortLinkResource(mapLinkService))
				.setCustomArgumentResolvers(pageableArgumentResolver).setControllerAdvice(exceptionTranslator)
				.setMessageConverters(jacksonMessageConverter).build();
	}
	
    @Test
    @Transactional
    public void testCreatLink() throws Exception {
        UrlDTO urlDTO = new UrlDTO();
        urlDTO.setUrl("www.facebook.com");   
        mockMvc.perform(
            post("/api/link")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(urlDTO)))
            .andExpect(status().isOk());

        assertThat(mapLinkRepository.findByUrl("www.facebook.com").getUrl().equals("www.facebook.com"));
    }
}
