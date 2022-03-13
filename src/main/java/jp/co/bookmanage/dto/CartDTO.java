package main.java.jp.co.bookmanage.dto;

import java.sql.Timestamp;

public class CartDTO {
	//カートNO
	private int CART_NO;
	//会員アカウント
	private String CART_MEMBER_ID;
	//図書NO
	private int CART_BOOK_NO;
	//数量
	private int CART_COUNT;
	//カート生成日時
	private Timestamp CART_GENERATE_DATE;
	
	public int getCART_NO() {
		return CART_NO;
	}
	public void setCART_NO(int cart_no) {
		CART_NO = cart_no;
	}
	public String getCART_MEMBER_ID() {
		return CART_MEMBER_ID;
	}
	public void setCART_MEMBER_ID(String cart_member_id) {
		CART_MEMBER_ID = cart_member_id;
	}
	public int getCART_BOOK_NO() {
		return CART_BOOK_NO;
	}
	public void setCART_BOOK_NO(int cart_book_no) {
		CART_BOOK_NO = cart_book_no;
	}
	public int getCART_COUNT() {
		return CART_COUNT;
	}
	public void setCART_COUNT(int cart_count) {
		CART_COUNT = cart_count;
	}
	public Timestamp getCART_GENERATE_DATE() {
		return CART_GENERATE_DATE;
	}
	public void setCART_GENERATE_DATE(Timestamp cart_generate_date) {
		CART_GENERATE_DATE = cart_generate_date;
	}
	
	

}