package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.domain.TblAggDtoClass;
import com.example.demo.domain.TblDtoClass;

public interface TblActionSrvc {

	
	/*
	 * insert
	 */
	public TblDtoClass insert(TblDtoClass tblDtoClass);
	/*
	 * select
	 */
	public List<TblDtoClass> selectAll(TblDtoClass tblDtoClass);
	
	/*
	 * 2018.07.20 pageNum을 통한 페이징 처리
	 * 
	 */
	public List<TblDtoClass> selectByPaging(int pageNum);
	/* 2018.07.20 pageable을 통한 페이징 처리
	 * 
	 */
	public Page<TblDtoClass>  selectByPagingUsingPageable(Pageable pageable);
	/*
	 * 자주 방문하는 사이트(Most Frequently Visited) 
	 */
	public List<TblAggDtoClass> selectMFV();
	/*
	 * * 가장 방문한지 오래된 사이트(Least Recently Visited) 
	 */
	public List<TblAggDtoClass> selectLRV();
}
