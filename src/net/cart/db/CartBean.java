package net.cart.db;

import java.util.Date;

public class CartBean {
	private int CART_NUM;
	private String CART_MEMBER_ID;
	private int CART_BOOK_NUM;
	private int CART_BOOK_AMOUNT;
	private String CART_BOOK_SIZE;
	private String CART_BOOK_COLOR;
	private Date CART_DATE;
	
	public int getCART_NUM() {
		return CART_NUM;
	}
	public void setCART_NUM(int cart_num) {
		CART_NUM = cart_num;
	}
	public String getCART_MEMBER_ID() {
		return CART_MEMBER_ID;
	}
	public void setCART_MEMBER_ID(String cart_member_id) {
		CART_MEMBER_ID = cart_member_id;
	}
	public int getCART_BOOK_NUM() {
		return CART_BOOK_NUM;
	}
	public void setCART_BOOK_NUM(int cart_book_num) {
		CART_BOOK_NUM = cart_book_num;
	}
	public int getCART_BOOK_AMOUNT() {
		return CART_BOOK_AMOUNT;
	}
	public void setCART_BOOK_AMOUNT(int cart_book_amount) {
		CART_BOOK_AMOUNT = cart_book_amount;
	}
	public String getCART_BOOK_SIZE() {
		return CART_BOOK_SIZE;
	}
	public void setCART_BOOK_SIZE(String cart_book_size) {
		CART_BOOK_SIZE = cart_book_size;
	}
	public String getCART_BOOK_COLOR() {
		return CART_BOOK_COLOR;
	}
	public void setCART_BOOK_COLOR(String cart_book_color) {
		CART_BOOK_COLOR = cart_book_color;
	}
	public Date getCART_DATE() {
		return CART_DATE;
	}
	public void setCART_DATE(Date cart_date) {
		CART_DATE = cart_date;
	}
}