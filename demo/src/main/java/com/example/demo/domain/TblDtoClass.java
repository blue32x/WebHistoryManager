package com.example.demo.domain;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;
/**
 * 2018.07.07
 * 
 * Table Dto Class 
 * 
 * 입출력값 관리 
 * 
 *테이블 컬럼
 *prcsDt   pk
 *seqNbr   pk
 *hostNm   pk
 *urlCntnt
 *lastChngTmstmp
 *lastChngGuid
 *
 * 
 * 
 * @author shchoi
 *
 */


@Document(collection = "tblDto")
public class TblDtoClass implements Serializable {
	/*
	 * 테이블 컬럼
	 */
	private String prcsDt;
	private int seqNbr;
	private String hostNm;
	private String urlCntnt;
	private String lastChngTmstmp;
	private String lastChngGuid;
	private int pageCnt;
	private int pageNum;
	private int totCnt;
	
	private static final long serialVersionUID = 1L;
	
	/*
	 * set Col
	 */
	public void setPrcsDt(String prcsDt)
	{
		this.prcsDt=prcsDt;
	}
	public void setSeqNbr(int seqNbr)
	{
		this.seqNbr=seqNbr;
	}
	public void setHostNm(String hostNm)
	{
		this.hostNm=hostNm;
	}
	public void setUrlCntnt(String urlCntnt)
	{
		this.urlCntnt=urlCntnt;
	}
	public void setLastChngTmstmp( String lastChngTmstmp)
	{
		this.lastChngTmstmp=lastChngTmstmp;
	}
	public void setLastChngGuid(String lastChngGuid)
	{
		this.lastChngGuid=lastChngGuid;
	}
	public void setPageCnt(int pageCnt)
	{
		this.pageCnt=pageCnt;
	}
	public void setPageNum(int pageNum)
	{
		this.pageNum=pageNum;
	}
	public void setTotCnt(int totCnt)
	{
		this.totCnt=totCnt;
	}
	/*
	 * get Col
	 */
	public String getPrcsDt()
	{
		return this.prcsDt;
	}
	public int getSeqNbr()
	{
		return this.seqNbr;
	}
	public String getHostNm()
	{
		return this.hostNm;
	}
	public String getUrlCntnt()
	{
		return this.urlCntnt;
	}
	public String getLastChngTmstmp()
	{
		return this.lastChngTmstmp;
	}
	public String getLastChngGuid()
	{
		return this.lastChngGuid;
	}
	public int getPageCnt()
	{
		return this.pageCnt;
	}
	public int getPageNum()
	{
		return this.pageNum;
	}
	public int getTotCnt()
	{
		return this.totCnt;
	}
}
