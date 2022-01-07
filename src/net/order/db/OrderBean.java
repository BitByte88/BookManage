package net.order.db;

import java.sql.Date;

public class OrderBean {
	private int ORDER_NUM;
	private String ORDER_TRADE_NUM;
	private String ORDER_TRANS_NUM;
	private int ORDER_BOOK_NUM;
	private String ORDER_BOOK_NAME;
	private int ORDER_BOOK_AMOUNT;
	private String ORDER_BOOK_SIZE;
	private String ORDER_BOOK_COLOR;
	private String ORDER_MEMBER_ID;
	private String ORDER_RECEIVE_NAME;
	private String ORDER_RECEIVE_ADDR1;
	private String ORDER_RECEIVE_ADDR2;
	private String ORDER_RECEIVE_PHONE;
	private String ORDER_RECEIVE_MOBILE;
	private String ORDER_MEMO;
	private int ORDER_SUM_MONEY;
	private String ORDER_TRADE_TYPE;
	private Date ORDER_TRADE_DATE;
	private String ORDER_TRADE_PAYER;
	private Date ORDER_DATE;
	private int ORDER_STATUS;
	
	public String getORDER_BOOK_NAME() {
		return ORDER_BOOK_NAME;
	}
	public void setORDER_BOOK_NAME(String order_book_name) {
		ORDER_BOOK_NAME = order_book_name;
	}
	public String getORDER_BOOK_SIZE() {
		return ORDER_BOOK_SIZE;
	}
	public void setORDER_BOOK_SIZE(String order_book_size) {
		ORDER_BOOK_SIZE = order_book_size;
	}
	public String getORDER_BOOK_COLOR() {
		return ORDER_BOOK_COLOR;
	}
	public void setORDER_BOOK_COLOR(String order_book_color) {
		ORDER_BOOK_COLOR = order_book_color;
	}
	public String getORDER_TRANS_NUM() {
		return ORDER_TRANS_NUM;
	}
	public void setORDER_TRANS_NUM(String order_trans_num) {
		ORDER_TRANS_NUM = order_trans_num;
	}
	public int getORDER_NUM() {
		return ORDER_NUM;
	}
	public void setORDER_NUM(int order_num) {
		ORDER_NUM = order_num;
	}
	public String getORDER_TRADE_NUM() {
		return ORDER_TRADE_NUM;
	}
	public void setORDER_TRADE_NUM(String order_trade_num) {
		ORDER_TRADE_NUM = order_trade_num;
	}
	public int getORDER_BOOK_NUM() {
		return ORDER_BOOK_NUM;
	}
	public void setORDER_BOOK_NUM(int order_book_num) {
		ORDER_BOOK_NUM = order_book_num;
	}
	public int getORDER_BOOK_AMOUNT() {
		return ORDER_BOOK_AMOUNT;
	}
	public void setORDER_BOOK_AMOUNT(int order_book_amount) {
		ORDER_BOOK_AMOUNT = order_book_amount;
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
	public String getORDER_RECEIVE_ADDR1() {
		return ORDER_RECEIVE_ADDR1;
	}
	public void setORDER_RECEIVE_ADDR1(String order_receive_addr1) {
		ORDER_RECEIVE_ADDR1 = order_receive_addr1;
	}
	public String getORDER_RECEIVE_ADDR2() {
		return ORDER_RECEIVE_ADDR2;
	}
	public void setORDER_RECEIVE_ADDR2(String order_receive_addr2) {
		ORDER_RECEIVE_ADDR2 = order_receive_addr2;
	}
	public String getORDER_RECEIVE_PHONE() {
		return ORDER_RECEIVE_PHONE;
	}
	public void setORDER_RECEIVE_PHONE(String order_receive_phone) {
		ORDER_RECEIVE_PHONE = order_receive_phone;
	}
	public String getORDER_RECEIVE_MOBILE() {
		return ORDER_RECEIVE_MOBILE;
	}
	public void setORDER_RECEIVE_MOBILE(String order_receive_mobile) {
		ORDER_RECEIVE_MOBILE = order_receive_mobile;
	}
	public String getORDER_MEMO() {
		return ORDER_MEMO;
	}
	public void setORDER_MEMO(String order_memo) {
		ORDER_MEMO = order_memo;
	}
	public int getORDER_SUM_MONEY() {
		return ORDER_SUM_MONEY;
	}
	public void setORDER_SUM_MONEY(int order_sum_money) {
		ORDER_SUM_MONEY = order_sum_money;
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
	public String getORDER_TRADE_PAYER() {
		return ORDER_TRADE_PAYER;
	}
	public void setORDER_TRADE_PAYER(String order_trade_payer) {
		ORDER_TRADE_PAYER = order_trade_payer;
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
}