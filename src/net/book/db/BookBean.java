package net.book.db;

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
	private int BOOK_PRICE;
	//画像
	private String BOOK_IMAGE;
	//ISBNコード
	private String BOOK_ISBN;
	
	public int getBOOK_NO() {
		return BOOK_NO;
	}
	public void setBOOK_NO(int book_no) {
		BOOK_NO = book_no;
	}
	public String getBOOK_CATEGORY() {
		return BOOK_CATEGORY;
	}
	public void setBOOK_CATEGORY(String book_category) {
		BOOK_CATEGORY = book_category;
	}
	public String getBOOK_NAME() {
		return BOOK_NAME;
	}
	public void setBOOK_NAME(String book_name) {
		BOOK_NAME = book_name;
	}
	public String getBOOK_WRITER() {
		return BOOK_WRITER;
	}
	public void setBOOK_WRITER(String book_writer) {
		BOOK_WRITER = book_writer;
	}
	public String getBOOK_PUBLISHER() {
		return BOOK_PUBLISHER;
	}
	public void setBOOK_PUBLISHER(String book_publisher) {
		BOOK_PUBLISHER = book_publisher;
	}
	public Date getBOOK_PUBLISHING_DATE() {
		return BOOK_PUBLISHING_DATE;
	}
	public void setBOOK_PUBLISHING_DATE(Date book_publishing_date) {
		BOOK_PUBLISHING_DATE = book_publishing_date;
	}
	public String getBOOK_CONTENT() {
		return BOOK_CONTENT;
	}
	public void setBOOK_CONTENT(String book_content) {
		BOOK_CONTENT = book_content;
	}
	public int getBOOK_PRICE() {
		return BOOK_PRICE;
	}
	public void setBOOK_PRICE(int book_price) {
		BOOK_PRICE = book_price;
	}
	public String getBOOK_IMAGE() {
		return BOOK_IMAGE;
	}
	public void setBOOK_IMAGE(String book_image) {
		BOOK_IMAGE = book_image;
	}
	public String getBOOK_ISBN() {
		return BOOK_ISBN;
	}
	public void setBOOK_ISBN(String book_isbn) {
		BOOK_ISBN = book_isbn;
	}
}