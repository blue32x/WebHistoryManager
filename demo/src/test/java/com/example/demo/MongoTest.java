package com.example.demo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.dao.TblRepository;
import com.example.demo.domain.TblDtoClass;

@RunWith(SpringRunner.class)
@DataMongoTest
public class MongoTest {

		@Autowired
		private TblRepository tblRepository;
		@Autowired
		private MongoTemplate mongoTemplate;
		private Date date;
		static String url ="https://www.stackoverflow.com/questions/39935456/spring-boot-application-cannot-be-resolved-to-a-type-but-i-imported-it-in-pom-xm";
		@Test
		public void mongoInsert()
		{  
			tblRepository.insert(_setDto(url));
			Assertions.assertThat((tblRepository.findAll().size())).isEqualTo(4) ;
		}
		
		@Test
		public void mongoselect()
		{
			tblRepository.insert(_setDto(url));
			tblRepository.insert(_setDto(url));
			tblRepository.insert(_setDto(url));
			tblRepository.findAll();
			Assertions.assertThat((tblRepository.findAll().get(0).getUrlCntnt())).isEqualTo(url) ;
			Assertions.assertThat((tblRepository.findAll().get(1).getUrlCntnt())).isEqualTo(url) ;
			Assertions.assertThat((tblRepository.findAll().get(2).getUrlCntnt())).isEqualTo(url) ;
		}
		
		
		private TblDtoClass _setDto(String url)
		{
			TblDtoClass tblDtoClass = new TblDtoClass();
			date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			
			tblDtoClass.setPrcsDt(sdf.format(date));
			/*
			 * 2018.07.09
			 * Pattern 활용 Regex 연습
			 * ^(http|https|ftp)://[a-z0-9A-Z]*(.)[a-z0-9A-Z]*(.)[a-z0-9A-Z.]*
			 */
			Pattern pattern =Pattern.compile("^(http|https|ftp)://[a-z0-9A-Z]*(.)[a-z0-9A-Z]*(.)[a-z0-9A-Z.]*");
			Matcher matcher = pattern.matcher(url);
			while(matcher.find())
			{
				String  hostNm= matcher.group();
				String[] hostSplit = hostNm.split("//");
				tblDtoClass.setHostNm(hostSplit[1]);
			}
			tblDtoClass.setUrlCntnt(url);
			tblDtoClass.setLastChngTmstmp(sdf2.format(date));
			tblDtoClass.setLastChngGuid(new java.rmi.dgc.VMID().toString());
			
			
			
			
			return tblDtoClass;
		}
}
