package com.example.demo.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.domain.TblDtoClass;
import com.example.demo.service.TblActionSrvc;

@RestController
public class ViewController {
	
	@Autowired
	private TblActionSrvc tblActionSrvc;
	private Logger logger = LoggerFactory.getLogger(ViewController.class);
	private Date date;
	
		
	
	
	@RequestMapping(value = "/test", method=RequestMethod.POST)
	public ModelAndView insertUrl( @RequestParam(value = "url")String url, ModelAndView mv)
	{
		logger.info("#SHCHOI insert");
		TblDtoClass tbl = _setDto(url);
		mv.setViewName("url");
		if(url.length() == 0 || url.isEmpty())
		{
			mv.addObject("result", tbl.getHostNm());
			return mv;
		}
		mv.addObject("result", tbl.getHostNm());
		/*
		 * 이력 적재 서비스 오퍼레이션 호출 
		 */
		
		tblActionSrvc.insert(_setDto(url));
		return mv;
	}
	
	@RequestMapping(value = "/select", method=RequestMethod.GET)
	public ModelAndView selectAll(ModelAndView mv)
	{
		logger.info("#SHCHOI select");
		mv.setViewName("table");
		mv.addObject("userList", tblActionSrvc.selectAll(_setDto("")));
		/*
		 * 전체 조회 서비스 오퍼레이션 호출 
		 */
		
		return mv;
	}
	/*
	 * 2018.07.20 
	 * pageNum,pageCnt를 통한 페이징 처리
	 */
	@RequestMapping(value = "/list/{pageNum}", method=RequestMethod.GET)
	public ModelAndView selectAllPaging01(@PathVariable("pageNum")int pageNum, ModelAndView mv)
	{
		logger.info("#SHCHOI list pageNum");
		mv.setViewName("table"); 
		mv.addObject("userList", tblActionSrvc.selectByPaging(pageNum));
		/*
		 * 전체 조회 서비스 오퍼레이션 호출 
		 */
		
		return mv;
	}
	
	
	/*
	 * 2018.07.20
	 * pageable 객체 사용
	 * 
	 */
	@RequestMapping(value = "/list", method=RequestMethod.GET)
	public ModelAndView selectAllPaging02(Pageable pageable, ModelAndView mv)
	{
		logger.info("#SHCHOI pageable test");
		mv.setViewName("table");
		mv.addObject("userList", tblActionSrvc.selectByPagingUsingPageable(pageable));
		/*
		 * 전체 조회 서비스 오퍼레이션 호출 
		 */
		
		return mv;
	}
	
	@RequestMapping(value = "/mfv", method=RequestMethod.GET)
	public ModelAndView selectMfv(ModelAndView mv)
	{
		logger.info("#SHCHOI select MFV");
		mv.setViewName("tableMFV");
		mv.addObject("userList", tblActionSrvc.selectMFV());
		/*
		 * 전체 조회 서비스 오퍼레이션 호출 
		 */
		return mv;
	}
	
	@RequestMapping(value = "/lrv", method=RequestMethod.GET)
	public ModelAndView selectLrv(ModelAndView mv)
	{
		logger.info("#SHCHOI select LRV ");
		mv.setViewName("tableLRV");
		mv.addObject("userList", tblActionSrvc.selectLRV());
		/*
		 * 전체 조회 서비스 오퍼레이션 호출 
		 */
		return mv;
	}
	
	@RequestMapping(value = "/init", method=RequestMethod.GET)
	public ModelAndView init(ModelAndView mv)
	{
		logger.info("#SHCHOI init");
		mv.setViewName("default");
		/*
		 * 전체 조회 서비스 오퍼레이션 호출 
		 */
		return mv;
	}
	
	
	/* 20180709
	 * 호출 되었을 때 기본 값 셋팅
	 */
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
		 * ^(https?):\/\/([^:\/\s]+)(:([^\/]*))?((\/[^\s/\/]+)*)?\/([^#\s\?]*)(\?([^#\s]*))?(#(\w*))?$
		 * ^(https?):\\/\\/([^:\\/\\s]+)(:([^\\/]*))?((\\/[^\\s/\\/]+)*)?\\/([^#\\s\\?]*)(\\?([^#\\s]*))?(#(\\w*))?$
		 */
		Pattern pattern =Pattern.compile("^(https?):\\/\\/([^:\\/\\s]+)(:([^\\/]*))?((\\/[^\\s/\\/]+)*)?\\/([^#\\s\\?]*)(\\?([^#\\s]*))?(#(\\w*))?$");
		Matcher matcher = pattern.matcher(url);
		if(matcher.matches())
		{
			
			tblDtoClass.setHostNm(matcher.group(2));
		}
		else
		{
			tblDtoClass.setHostNm("unKnown");
		}
		tblDtoClass.setUrlCntnt(url);
		tblDtoClass.setLastChngTmstmp(sdf2.format(date));
		tblDtoClass.setLastChngGuid(new java.rmi.dgc.VMID().toString());
		
		
		
		
		return tblDtoClass;
	}
}
