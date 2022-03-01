package net.admin.book.db;

import java.math.BigDecimal;
import java.sql.Date;

public class BookBean {
	//図書NO
	private int BOOK_NO;
	//カテゴリー
	private String BOOK_CATEGORY;
	//書名
	private String BOOK_NAME;
	//著者
	private String BOOK_WRITER;
	//出版社
	private String BOOK_PUBLISHER;
	//発行日
	private Date BOOK_PUBLISHING_DATE;
	//図書内容
	private String BOOK_CONTENT;
	//販売価格
	private BigDecimal BOOK_PRICE;
	//イメージ
	private String BOOK_IMAGE;
	//ISBNコード
	private String BOOK_ISBN;
	//削除フラグ
	private Integer DELETE_FLAG;
	//作成者
	private Integer CREATE_USER;
	//作成日時
	private Date CREATE_DATE;
	//更新社
	private Integer UPDATE_USER;
	//更新日時
	private Date UPDATE_DATE;
	
	public int getBOOK_NO() {
		return BOOK_NO;
	}
	public void setBOOK_NO(int bOOK_NO) {
		BOOK_NO = bOOK_NO;
	}
	public String getBOOK_CATEGORY() {
		return BOOK_CATEGORY;
	}
	public void setBOOK_CATEGORY(String bOOK_CATEGORY) {
		BOOK_CATEGORY = bOOK_CATEGORY;
	}
	public String getBOOK_NAME() {
		return BOOK_NAME;
	}
	public void setBOOK_NAME(String bOOK_NAME) {
		BOOK_NAME = bOOK_NAME;
	}
	public String getBOOK_WRITER() {
		return BOOK_WRITER;
	}
	public void setBOOK_WRITER(String bOOK_WRITER) {
		BOOK_WRITER = bOOK_WRITER;
	}
	public String getBOOK_PUBLISHER() {
		return BOOK_PUBLISHER;
	}
	public void setBOOK_PUBLISHER(String bOOK_PUBLISHER) {
		BOOK_PUBLISHER = bOOK_PUBLISHER;
	}
	public Date getBOOK_PUBLISHING_DATE() {
		return BOOK_PUBLISHING_DATE;
	}
	public void setBOOK_PUBLISHING_DATE(Date bOOK_PUBLISHING_DATE) {
		BOOK_PUBLISHING_DATE = bOOK_PUBLISHING_DATE;
	}
	public String getBOOK_CONTENT() {
		return BOOK_CONTENT;
	}
	public void setBOOK_CONTENT(String bOOK_CONTENT) {
		BOOK_CONTENT = bOOK_CONTENT;
	}
	public BigDecimal getBOOK_PRICE() {
		return BOOK_PRICE;
	}
	public void setBOOK_PRICE(BigDecimal bOOK_PRICE) {
		BOOK_PRICE = bOOK_PRICE;
	}
	public String getBOOK_IMAGE() {
		return BOOK_IMAGE;
	}
	public void setBOOK_IMAGE(String bOOK_IMAGE) {
		BOOK_IMAGE = bOOK_IMAGE;
	}
	public String getBOOK_ISBN() {
		return BOOK_ISBN;
	}
	public void setBOOK_ISBN(String bOOK_ISBN) {
		BOOK_ISBN = bOOK_ISBN;
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