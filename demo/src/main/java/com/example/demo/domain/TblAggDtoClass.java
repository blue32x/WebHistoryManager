package com.example.demo.domain;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;
/**
 * url 이력 집계 도메인
 * @author shchoi
 *
 */
@Document(collection = "tblAggDto")
public class TblAggDtoClass implements Serializable  {
	private String hostNm;
	private String lastChngTmstmp;
	private int seqNbr;
	private int cnt;
	private static final long serialVersionUID = 1L;
	
	
	public void setHostNm(String hostNm)
	{
		this.hostNm=hostNm;
	}
	
	public void setCnt(int cnt)
	{
		this.cnt=cnt;
	}
	
	public void setSeqNbr(int seqNbr)
	{
		this.seqNbr=seqNbr;
	}
	
	public void setLastChngTmstmp( String lastChngTmstmp)
	{
		this.lastChngTmstmp=lastChngTmstmp;
	}
	
	public String getLastChngTmstmp()
	{
		return this.lastChngTmstmp;
	}
	
	public int getCnt()
	{
		return this.cnt;
	}
	
	public String getHostNm()
	{
		return this.hostNm;
	}
	
	public int getSeqNbr()
	{
		return this.seqNbr;
	}
}
