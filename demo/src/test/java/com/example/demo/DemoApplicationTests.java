package com.example.demo;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.controller.ViewController;
import com.example.demo.domain.TblDtoClass;
import com.example.demo.service.TblActionSrvc;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebAppConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	private TblDtoClass tblDtoClass ;
	@Autowired
	 private ViewController viewController;
	@Autowired
	 private WebApplicationContext wac;
	@Autowired
	 private ObjectMapper objectMapper;
	@MockBean
	private TblActionSrvc tblActionSrvc;
	private MockMvc mock;
	
	static String url ="https://stackoverflow.com/questions/39935456/spring-boot-application-cannot-be-resolved-to-a-type-but-i-imported-it-in-pom-xm";
	
	@Before
	public void setUp() 
	{
		this.mock=MockMvcBuilders.webAppContextSetup(wac).build();
		//this.mock=MockMvcBuilders.standaloneSetup(viewController).build();
		tblDtoClass = new TblDtoClass();
		tblDtoClass.setUrlCntnt(url);
	}
	
	
	@Test
	public void insertTest01() throws JsonProcessingException, Exception {
		
		
		/*
		 * 테스트 입력 값
		 */
		ResultActions resultActions = 
				mock.perform(MockMvcRequestBuilders.post("/test").param("url", url)
						.contentType(MediaType.APPLICATION_JSON)
						.content(TblDtoClass.class.toString())
						
						
						
						);
		resultActions.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk());

	}
	
	@Test
	public void selectTest01() throws Exception {
		/*
		 * 테스트 입력 값
		 */
		/*
		 * 테스트 입력 값
		 */
		ResultActions resultActions = 
				mock.perform(MockMvcRequestBuilders.get("/lrv")
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.accept(MediaType.APPLICATION_JSON)
						
						
						
						);
		resultActions.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk());
//		TblDtoClass tblDtoClass = new TblDtoClass();
//		String url ="https://stackoverflow.com/questions/39935456/spring-boot-application-cannot-be-resolved-to-a-type-but-i-imported-it-in-pom-xm";
//		this.viewController.insertUrl(url, new ModelAndView());
//		ModelAndView mv =this.viewController.selectAll(new ModelAndView());
		
	}

	
	@Test
	public void selectMFVTest01() throws Exception {
		/*
		 * 테스트 입력 값
		 */
		/*
		 * 테스트 입력 값
		 */
		ResultActions resultActions = 
				mock.perform(MockMvcRequestBuilders.get("/mfv")
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.accept(MediaType.APPLICATION_JSON)
						
						
						
						);
		resultActions.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk());
//		TblDtoClass tblDtoClass = new TblDtoClass();
//		String url ="https://stackoverflow.com/questions/39935456/spring-boot-application-cannot-be-resolved-to-a-type-but-i-imported-it-in-pom-xm";
//		this.viewController.insertUrl(url, new ModelAndView());
//		ModelAndView mv =this.viewController.selectAll(new ModelAndView());
		
	}
	
	@Test
	public void selectLRVTest01() throws Exception {
		/*
		 * 테스트 입력 값
		 */
		/*
		 * 테스트 입력 값
		 */
		ResultActions resultActions = 
				mock.perform(MockMvcRequestBuilders.get("/select")
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.accept(MediaType.APPLICATION_JSON)
						
						
						
						);
		resultActions.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk());
//		TblDtoClass tblDtoClass = new TblDtoClass();
//		String url ="https://stackoverflow.com/questions/39935456/spring-boot-application-cannot-be-resolved-to-a-type-but-i-imported-it-in-pom-xm";
//		this.viewController.insertUrl(url, new ModelAndView());
//		ModelAndView mv =this.viewController.selectAll(new ModelAndView());
		
	}
}
