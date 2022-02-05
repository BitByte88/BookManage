package net.order.db;

import java.sql.Date;

public class OrderBean {
	private int ORDER_NO;
	private String ORDER_TRANS_NO;
	private int ORDER_BOOK_NO;
	private int ORDER_COUNT;
	private String ORDER_MEMBER_ID;
	private String ORDER_RECEIVE_NAME;
	private String ORDER_RECEIVE_NAME_KANA;
	private String ORDER_RECEIVE_EMAIL;
	private String ORDER_RECEIVE_TEL;
	private String ORDER_RECEIVE_ZIPCODE;
	private String ORDER_RECEIVE_ADD_1;
	private String ORDER_RECEIVE_ADD_2;
	private String ORDER_RECEIVE_ADD_3;
	private String ORDER_MEMO;
	private String ORDER_TRADE_TYPE;
	private Date ORDER_TRADE_DATE;	
	private Date ORDER_DATE;	
	private int ORDER_STATUS;
	private int TOTAL_PRICE;
	private String BOOK_NAME;
	private int BOOK_PRICE;
	
	public int getORDER_NO() {
		return ORDER_NO;
	}
	public void setORDER_NO(int order_no) {
		ORDER_NO = order_no;
	}
	public String getORDER_TRANS_NO() {
		return ORDER_TRANS_NO;
	}
	public void setORDER_TRANS_NO(String order_trans_no) {
		ORDER_TRANS_NO = order_trans_no;
	}
	public int getORDER_BOOK_NO() {
		return ORDER_BOOK_NO;
	}
	public void setORDER_BOOK_NO(int order_book_no) {
		ORDER_BOOK_NO = order_book_no;
	}
	public int getORDER_COUNT() {
		return ORDER_COUNT;
	}
	public void setORDER_COUNT(int order_count) {
		ORDER_COUNT = order_count;
	}
	public String getORDER_MEMBER_ID() {
		return ORDER_MEMBER_ID;
	}
	public void setORDER_MEMBER_ID(String order_member_id) {
		ORDER_MEMBER_ID = order_member_id;
	}
	public String getORDER_RECEIVE_NAME() {
		return ORDER_RECEIVE_NAME;
	}
	public void setORDER_RECEIVE_NAME(String order_receive_name) {
		ORDER_RECEIVE_NAME = order_receive_name;
	}
	public String getORDER_RECEIVE_NAME_KANA() {
		return ORDER_RECEIVE_NAME_KANA;
	}
	public void setORDER_RECEIVE_NAME_KANA(String order_receive_name_kana) {
		ORDER_RECEIVE_NAME_KANA = order_receive_name_kana;
	}
	public String getORDER_RECEIVE_EMAIL() {
		return ORDER_RECEIVE_EMAIL;
	}
	public void setORDER_RECEIVE_EMAIL(String order_receive_email) {
		ORDER_RECEIVE_EMAIL = order_receive_email;
	}
	public String getORDER_RECEIVE_TEL() {
		return ORDER_RECEIVE_TEL;
	}
	public void setORDER_RECEIVE_TEL(String order_receive_tel) {
		ORDER_RECEIVE_TEL = order_receive_tel;
	}
	public String getORDER_RECEIVE_ZIPCODE() {
		return ORDER_RECEIVE_ZIPCODE;
	}
	public void setORDER_RECEIVE_ZIPCODE(String order_receive_zipcode) {
		ORDER_RECEIVE_ZIPCODE = order_receive_zipcode;
	}
	public String getORDER_RECEIVE_ADD_1() {
		return ORDER_RECEIVE_ADD_1;
	}
	public void setORDER_RECEIVE_ADD_1(String order_receive_add_1) {
		ORDER_RECEIVE_ADD_1 = order_receive_add_1;
	}
	public String getORDER_RECEIVE_ADD_2() {
		return ORDER_RECEIVE_ADD_2;
	}
	public void setORDER_RECEIVE_ADD_2(String order_receive_add_2) {
		ORDER_RECEIVE_ADD_2 = order_receive_add_2;
	}
	public String getORDER_RECEIVE_ADD_3() {
		return ORDER_RECEIVE_ADD_3;
	}
	public void setORDER_RECEIVE_ADD_3(String order_receive_add_3) {
		ORDER_RECEIVE_ADD_3 = order_receive_add_3;
	}
	public String getORDER_MEMO() {
		return ORDER_MEMO;
	}
	public void setORDER_MEMO(String order_memo) {
		ORDER_MEMO = order_memo;
	}
	public String getORDER_TRADE_TYPE() {
		return ORDER_TRADE_TYPE;
	}
	public void setORDER_TRADE_TYPE(String order_trade_type) {
		ORDER_TRADE_TYPE = order_trade_type;
	}
	public Date getORDER_TRADE_DATE() {
		return ORDER_TRADE_DATE;
	}
	public void setORDER_TRADE_DATE(Date order_trade_date) {
		ORDER_TRADE_DATE = order_trade_date;
	}
	public Date getORDER_DATE() {
		return ORDER_DATE;
	}
	public void setORDER_DATE(Date order_date) {
		ORDER_DATE = order_date;
	}
	public int getORDER_STATUS() {
		return ORDER_STATUS;
	}
	public void setORDER_STATUS(int order_status) {
		ORDER_STATUS = order_status;
	}
	public int getTOTAL_PRICE() {
		return TOTAL_PRICE;
	}
	public void setTOTAL_PRICE(int total_price) {
		TOTAL_PRICE = total_price;
	}
	public String getBOOK_NAME() {
		return BOOK_NAME;
	}
	public void setBOOK_NAME(String book_name) {
		BOOK_NAME = book_name;
	}
	public int getBOOK_PRICE() {
		return BOOK_PRICE;
	}
	public void setBOOK_PRICE(int book_price) {
		BOOK_PRICE = book_price;
	}


}