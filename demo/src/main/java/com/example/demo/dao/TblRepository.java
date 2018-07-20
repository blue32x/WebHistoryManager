package com.example.demo.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.domain.TblDtoClass;



public interface TblRepository extends MongoRepository<TblDtoClass, Long>  {
	
	/*
	 * insert
	 */
	public TblDtoClass insert(TblDtoClass tblDtoClass);
	/*
	 * select
	 */
	public List<TblDtoClass> findAll();
	
	public Page<TblDtoClass> findAll(Pageable pageable);

}
