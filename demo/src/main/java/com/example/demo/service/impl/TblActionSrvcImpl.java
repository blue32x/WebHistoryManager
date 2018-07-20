package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.LimitOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SkipOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
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
		ProjectionOperation po = Aggregation.project("cnt").and("hostNm").previousOperation();
		GroupOperation go = Aggregation.group("hostNm").count().as("cnt"); //group by hostNm , 각각 hostNm count
		/*
		 * 앞의  항목들을 조합하여  쿼리 완성
		 * select hostNm ,count(*)
		 * from db
		 * group by hostNm
		 */
		
		/*
		 * 2018.07.20 mongoDB 로직 개선 
		 * SortOperation은 정상 작동 중이었음 dataTables에서 호스트명으로 다시 소팅 하는 것 같음
		 * Aggregation.newAggregation(mo,go,so,lo,po); 순서 유의할 것 
		 * mo -> where
		 * go -> group by
		 * so -> order by
		 * lo -> rownum
		 * po -> 전체 항목에 대한 조회가 아닐 경우 지정해서 조회할 수 있음. 
		 *
		 * 
		 */
		SortOperation so = Aggregation.sort(Sort.Direction.DESC,"cnt");
		LimitOperation lo = Aggregation.limit(5);
		/*
		 * 2017.07.20 mongoDB 로직 개선 END
		 */
		
		
		
		
		Aggregation aggregation = Aggregation.newAggregation(mo,go,so,lo,po);
		AggregationResults<TblAggDtoClass> aggResults =  mongoTemplate.aggregate(aggregation,TblDtoClass.class ,TblAggDtoClass.class);
		
		/*
		 * 쿼리 결과를 list에 반환함
		 * 
		 */
		List<TblAggDtoClass> list= aggResults.getMappedResults();
		
//		/*
//		 * 정렬을 위해서 arrayList에  기존의 목록을 담음.
//		 */
//		newList.addAll(aggResults.getMappedResults());
//		/*
//		 * Most Frequently Visited
//		 * 내림차순 정렬
//		 * 
//		 */
//		newList.sort(new Comparator<TblAggDtoClass>() {
//			
//			@Override
//			public int compare(TblAggDtoClass o1, TblAggDtoClass o2)
//			{
//				if(o1.getCnt() > o2.getCnt())
//				{
//					return -1;
//				}
//				else if(o1.getCnt() < o2.getCnt())
//				{
//					return 1;
//				}
//				else
//				{
//					return 0;
//				}
//			}
//		});
//		if(newList.size() <6)
//		{
//			return newList;
//		}
//		else
//		{
//			return newList.subList(0, 5);
//		}
		
		return list;
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
		//hostNm  셋팅
		ProjectionOperation po = Aggregation.project("lastChngTmstmp").and("hostNm").previousOperation();
		
		/*
		 * 2018.07.19 mongoDB 로직 개선 
		 * SortOperation은 정상 작동 중이었음 dataTables에서 호스트명으로 다시 소팅 하는 것 같음
		 * Aggregation.newAggregation(mo,go,so,lo,po); 순서 유의할 것 
		 * mo -> where
		 * go -> group by
		 * so -> order by
		 * lo -> rownum
		 * po -> 전체 항목에 대한 조회가 아닐 경우 지정해서 조회할 수 있음. 
		 *
		 * 
		 */
		SortOperation so = Aggregation.sort(Sort.Direction.DESC,Aggregation.previousOperation());
		LimitOperation lo = Aggregation.limit(5);
		/*
		 * 2017.07.19 mongoDB 로직 개선 END
		 */
		Aggregation aggregation = Aggregation.newAggregation(mo,go,so,lo,po);
		AggregationResults<TblAggDtoClass> aggResults =  mongoTemplate.aggregate(aggregation,TblDtoClass.class ,TblAggDtoClass.class);
		List<TblAggDtoClass> list= aggResults.getMappedResults();
//		/*
//		 * 정렬을 위해 새로운 ArrayList 정의
//		 */
//		newList.addAll(list);
//		newList.sort(new Comparator<TblAggDtoClass>() {
//			
//				@Override
//				public int compare(TblAggDtoClass o1, TblAggDtoClass o2)
//				{
//					if(o1.getLastChngTmstmp().compareTo(o2.getLastChngTmstmp()) > 0)
//					{
//						return 1;
//					}
//					else if(o1.getLastChngTmstmp().compareTo(o2.getLastChngTmstmp()) < 0)
//					{
//						return -1;
//					}
//					else
//					{
//						return 0;
//					}
//				}
//		});
//		
//		//List<TblDtoClass> list =tblmngr.findAll();
//		if(newList.size() <6)
//		{
//			return newList;
//		}
//		else
//		{
//			return newList.subList(0, 5);
//		}
		
		
		return list;
	}
	/*
	 * 2018.07.20
	 * pageNum을 통한 페이징 처리
	 */
	@Override
	public List<TblDtoClass> selectByPaging(int pageNum) {
		// TODO Auto-generated method stub
		int pageCnt=3;
		logger.info("#SHCHOI# pageNum : {}",pageNum);
		logger.info("#SHCHOI# pageCnt : {}",pageCnt);
		SkipOperation so =null;
		if(pageNum == 1)
		{
			so = Aggregation.skip(1); 
		}
		else
		{
			so = Aggregation.skip(pageCnt * ((pageNum-1)));
		}
		logger.info("#SHCHOI# so is OK ");
		LimitOperation lo = Aggregation.limit(pageCnt);
		Aggregation aggregation = Aggregation.newAggregation(so,lo);
		AggregationResults<TblDtoClass> aggResults =  mongoTemplate.aggregate(aggregation,TblDtoClass.class ,TblDtoClass.class);
		List<TblDtoClass> list= aggResults.getMappedResults();
		
		
		return list;
	}
	
	/*
	 * 2018.07.20
	 * pageable 객체를  통한 페이징 처리
	 */
	@Override
	public Page<TblDtoClass> selectByPagingUsingPageable(Pageable pageable) {
		// TODO Auto-generated method stub
		Page<TblDtoClass> pages =tblmngr.findAll(pageable);
		return pages;
	}

}
