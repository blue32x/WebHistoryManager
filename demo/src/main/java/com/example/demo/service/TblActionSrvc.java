package com.example.demo.service;

import java.util.List;

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
	 * 자주 방문하는 사이트(Most Frequently Visited) 
	 */
	public List<TblAggDtoClass> selectMFV();
	/*
	 * * 가장 방문한지 오래된 사이트(Least Recently Visited) 
	 */
	public List<TblAggDtoClass> selectLRV();
}
