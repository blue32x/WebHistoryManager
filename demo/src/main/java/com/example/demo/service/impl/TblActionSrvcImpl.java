package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.example.demo.dao.TblRepository;
import com.example.demo.domain.TblAggDtoClass;
import com.example.demo.domain.TblDtoClass;
import com.example.demo.service.TblActionSrvc;

@Service
public class TblActionSrvcImpl implements TblActionSrvc{

	private Logger logger = LoggerFactory.getLogger(TblActionSrvcImpl.class);
	@Autowired
	private TblRepository tblmngr;
	@Autowired
	private MongoTemplate mongoTemplate;
	
	
	@Override
	public TblDtoClass insert(TblDtoClass tblDtoClass) {
		// TODO Auto-generated method stub
		
		List<TblDtoClass> list =tblmngr.findAll();
		/*
		 * 일련번호 증번
		 */
		tblDtoClass.setSeqNbr(list.size()+1);
		
		tblmngr.insert(tblDtoClass);
		return tblDtoClass;
	}

	@Override
	public List<TblDtoClass> selectAll(TblDtoClass tblDtoClass) {
		// TODO Auto-generated method stub
		
		List<TblDtoClass> list =tblmngr.findAll();
		List<TblDtoClass> newlist=new ArrayList<TblDtoClass>();		
		newlist.addAll(list);
		newlist.sort(new Comparator<TblDtoClass>() {
			
			@Override
			public int compare(TblDtoClass o1, TblDtoClass o2)
			{
				if(o1.getLastChngTmstmp().compareTo(o2.getLastChngTmstmp()) > 0)
				{
					return -1;
				}
				else if(o1.getLastChngTmstmp().compareTo(o2.getLastChngTmstmp()) < 0)
				{
					return 1;
				}
				else
				{
					return 0;
				}
			}
	});
		//list.subList((pageNum-1)*pageCnt, (pageNum-1)*pageCnt+pageCnt)
		return newlist;
	}

	@Override
	public List<TblAggDtoClass> selectMFV() {
		/*
		 * 쿼리의 결과를 담을 ArrayList
		 */
		List<TblAggDtoClass> newList = new ArrayList<TblAggDtoClass>();
		Criteria criteria = new Criteria();
		MatchOperation mo = Aggregation.match(criteria); //전체 조회 
		GroupOperation go = Aggregation.group("hostNm").count().as("cnt"); //group by hostNm , 각각 hostNm count
		ProjectionOperation po = Aggregation.project("cnt").and("hostNm").previousOperation();
		/*
		 * 앞의  항목들을 조합하여  쿼리 완성
		 * select hostNm ,count(*)
		 * from db
		 * group by hostNm
		 */
		Aggregation aggregation = Aggregation.newAggregation(mo,go,po);
		AggregationResults<TblAggDtoClass> aggResults =  mongoTemplate.aggregate(aggregation,TblDtoClass.class ,TblAggDtoClass.class);
		
		/*
		 * 쿼리 결과를 list에 반환함
		 * 
		 */
		List<TblAggDtoClass> list= aggResults.getMappedResults();
		
		/*
		 * 정렬을 위해서 arrayList에  기존의 목록을 담음.
		 */
		newList.addAll(aggResults.getMappedResults());
		/*
		 * Most Frequently Visited
		 * 내림차순 정렬
		 * 
		 */
		newList.sort(new Comparator<TblAggDtoClass>() {
			
			@Override
			public int compare(TblAggDtoClass o1, TblAggDtoClass o2)
			{
				if(o1.getCnt() > o2.getCnt())
				{
					return -1;
				}
				else if(o1.getCnt() < o2.getCnt())
				{
					return 1;
				}
				else
				{
					return 0;
				}
			}
		});
		if(newList.size() <6)
		{
			return newList;
		}
		else
		{
			return newList.subList(0, 5);
		}
	}

	@Override
	public List<TblAggDtoClass> selectLRV() {
		// TODO Auto-generated method stub
		List<TblAggDtoClass> newList = new ArrayList<TblAggDtoClass>();
		Criteria criteria = new Criteria();
		
		/*
		 * 전체 조회
		 */
		MatchOperation mo = Aggregation.match(criteria); //전체 조회 
		/*
		 * hostNm으로 group by  후 가장 최근 방문시간으로 조회
		 */
		GroupOperation go = Aggregation.group("hostNm").max("lastChngTmstmp").as("lastChngTmstmp");
		//SortOperation so = Aggregation.sort(Sort.Direction.DESC,"lastChngTmstmp");
		//hostNm  셋팅
		ProjectionOperation po = Aggregation.project("lastChngTmstmp").and("hostNm").previousOperation();
		Aggregation aggregation = Aggregation.newAggregation(mo,go,po);
		AggregationResults<TblAggDtoClass> aggResults =  mongoTemplate.aggregate(aggregation,TblDtoClass.class ,TblAggDtoClass.class);
		List<TblAggDtoClass> list= aggResults.getMappedResults();
		/*
		 * 정렬을 위해 새로운 ArrayList 정의
		 */
		newList.addAll(list);
		newList.sort(new Comparator<TblAggDtoClass>() {
			
				@Override
				public int compare(TblAggDtoClass o1, TblAggDtoClass o2)
				{
					if(o1.getLastChngTmstmp().compareTo(o2.getLastChngTmstmp()) > 0)
					{
						return 1;
					}
					else if(o1.getLastChngTmstmp().compareTo(o2.getLastChngTmstmp()) < 0)
					{
						return -1;
					}
					else
					{
						return 0;
					}
				}
		});
		
		//List<TblDtoClass> list =tblmngr.findAll();
		if(newList.size() <6)
		{
			return newList;
		}
		else
		{
			return newList.subList(0, 5);
		}
	}

}
