package com.ccd.models;

import java.sql.Time;

/**
 * 
 * Model to carry Vital sign information.
 */
public class VitalSign {
	private String date;
	private Time time;
	/**
	 * format MM/Hg
	 */
	private String bp;
	private String systolic;
	private String diastolic;
	/**
	 * Based on per Minute
	 */
	private int pulse;
	/**
	 * Based on per Minute
	 */
	private int resp;
	private Double temp;

	/**
	 * unit cm
	 */
	private long htFt;
	private long htIn;
	
	private Double wtLb;
	/**
	 * Based on per kg/m2
	 */
	private Double bmi;
	private Double bsa;

	public long getHtFt() {
		return htFt;
	}

	public void setHtFt(long htFt) {
		this.htFt = htFt;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}
	
	public String getBp() {
	    if(systolic==null){
	  		systolic="";
	  	    }
	  	    if(diastolic==null){
	  		diastolic="";
	  	    }
	  	    if(systolic==null && diastolic==null ){
	  		return null;
	  	    }
	  	  if(systolic.equals("") && diastolic.equals("") ){
	  		return null;
	  	    }
	  		return systolic+"/"+diastolic;
	}

	public void setBp() {
	    if(systolic==null){
		systolic="";
	    }
	    if(diastolic==null){
		diastolic="";
	    }
		this.bp = systolic+"/"+diastolic;;
	}
	public void setBp(String bp) {
		this.bp = bp;
	}
	public int getPulse() {
		return pulse;
	}

	public void setPulse(int pulse) {
		this.pulse = pulse;
	}

	public int getResp() {
		return resp;
	}

	public void setResp(int resp) {
		this.resp = resp;
	}

	public Double getTemp() {
		return temp;
	}

	public void setTemp(Double temp) {
		this.temp = temp;
	}

	
	public long getHtIn() {
		return htIn;
	}

	public void setHtIn(long htIn) {
		this.htIn = htIn;
	}

	public Double getWtLb() {
		return wtLb;
	}

	public void setWtLb(Double wtLb) {
		this.wtLb = wtLb;
	}

	public Double getBmi() {
		return bmi;
	}

	public void setBmi(Double bmi) {
		this.bmi = bmi;
	}

	public Double getBsa() {
		return bsa;
	}

	public void setBsa(Double bsa) {
		this.bsa = bsa;
	}

	/**
	 * @return the systolic
	 */
	public String getSystolic() {
	    return systolic;
	}

	/**
	 * @param systolic the systolic to set
	 */
	public void setSystolic(String systolic) {
	    this.systolic = systolic;
	    this.setBp();
	}

	/**
	 * @return the diastolic
	 */
	public String getDiastolic() {
	    return diastolic;
	}

	/**
	 * @param diastolic the diastolic to set
	 */
	public void setDiastolic(String diastolic) {
	    this.diastolic = diastolic;
	    this.setBp();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
	    return "VitalSign [date=" + date + ", time=" + time + ", bp=" + getBp() + ", systolic=" + systolic
		    + ", diastolic=" + diastolic + ", pulse=" + pulse + ", resp=" + resp + ", temp=" + temp + ", htFt="
		    + htFt + ", htIn=" + htIn + ", wtLb=" + wtLb + ", bmi=" + bmi + ", bsa=" + bsa + "]";
	}

	
	
}
