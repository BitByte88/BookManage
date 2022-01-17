package net.member.db;
import java.sql.*;
public class MemberBean {

	private String MEMBER_ID;
	private String MEMBER_PW;
	private String MEMBER_NAME;
	private String MEMBER_NAME_KANA;
	private String MEMBER_TEL;
	private String MEMBER_MAIL;
	private String MEMBER_ZIPCODE;
	private String MEMBER_ADD_1;
	private String MEMBER_ADD_2;
	private String MEMBER_ADD_3;
	private Integer MEMBER_TYPE;
	private Integer DELETE_FLAG;
	private Integer CREATE_USER;
	private Date CREATE_DATE;
	private Integer UPDATE_USER;
	private Date UPDATE_DATE;
	
	
	public String getMEMBER_ID() {
		return MEMBER_ID;
	}
	public void setMEMBER_ID(String mEMBER_ID) {
		MEMBER_ID = mEMBER_ID;
	}
	public String getMEMBER_PW() {
		return MEMBER_PW;
	}
	public void setMEMBER_PW(String mEMBER_PW) {
		MEMBER_PW = mEMBER_PW;
	}
	public String getMEMBER_NAME() {
		return MEMBER_NAME;
	}
	public void setMEMBER_NAME(String mEMBER_NAME) {
		MEMBER_NAME = mEMBER_NAME;
	}
	public String getMEMBER_NAME_KANA() {
		return MEMBER_NAME_KANA;
	}
	public void setMEMBER_NAME_KANA(String mEMBER_NAME_KANA) {
		MEMBER_NAME_KANA = mEMBER_NAME_KANA;
	}
	public String getMEMBER_TEL() {
		return MEMBER_TEL;
	}
	public void setMEMBER_TEL(String mEMBER_TEL) {
		MEMBER_TEL = mEMBER_TEL;
	}
	public String getMEMBER_MAIL() {
		return MEMBER_MAIL;
	}
	public void setMEMBER_MAIL(String mEMBER_MAIL) {
		MEMBER_MAIL = mEMBER_MAIL;
	}
	public String getMEMBER_ZIPCODE() {
		return MEMBER_ZIPCODE;
	}
	public void setMEMBER_ZIPCODE(String mEMBER_ZIPCODE) {
		MEMBER_ZIPCODE = mEMBER_ZIPCODE;
	}
	public String getMEMBER_ADD_1() {
		return MEMBER_ADD_1;
	}
	public void setMEMBER_ADD_1(String mEMBER_ADD_1) {
		MEMBER_ADD_1 = mEMBER_ADD_1;
	}
	public String getMEMBER_ADD_2() {
		return MEMBER_ADD_2;
	}
	public void setMEMBER_ADD_2(String mEMBER_ADD_2) {
		MEMBER_ADD_2 = mEMBER_ADD_2;
	}
	public String getMEMBER_ADD_3() {
		return MEMBER_ADD_3;
	}
	public void setMEMBER_ADD_3(String mEMBER_ADD_3) {
		MEMBER_ADD_3 = mEMBER_ADD_3;
	}
	public Integer getMEMBER_TYPE() {
		return MEMBER_TYPE;
	}
	public void setMEMBER_TYPE(Integer mEMBER_TYPE) {
		MEMBER_TYPE = mEMBER_TYPE;
	}
	public Integer getDELETE_FLAG() {
		return DELETE_FLAG;
	}
	public void setDELETE_FLAG(Integer dELETE_FLAG) {
		DELETE_FLAG = dELETE_FLAG;
	}
	public Integer getCREATE_USER() {
		return CREATE_USER;
	}
	public void setCREATE_USER(Integer cREATE_USER) {
		CREATE_USER = cREATE_USER;
	}
	public Date getCREATE_DATE() {
		return CREATE_DATE;
	}
	public void setCREATE_DATE(Date cREATE_DATE) {
		CREATE_DATE = cREATE_DATE;
	}
	public Integer getUPDATE_USER() {
		return UPDATE_USER;
	}
	public void setUPDATE_USER(Integer uPDATE_USER) {
		UPDATE_USER = uPDATE_USER;
	}
	public Date getUPDATE_DATE() {
		return UPDATE_DATE;
	}
	public void setUPDATE_DATE(Date uPDATE_DATE) {
		UPDATE_DATE = uPDATE_DATE;
	}	
}