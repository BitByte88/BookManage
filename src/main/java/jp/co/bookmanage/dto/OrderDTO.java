package main.java.jp.co.bookmanage.dto;

import java.sql.Date;

public class OrderDTO {
	//注文NO
	private int ORDER_NO;
	//アイテムNO
	private int ORDER_ITEM_NO;
	//図書NO
	private int ORDER_BOOK_NO;
	//注文ステータス
	private int ORDER_STATUS;
	//数量
	private int ORDER_COUNT;
	//アカウント
	private String ORDER_MEMBER_ID;
	//メモー
	private String ORDER_MEMO;
	//決済方法
	private String ORDER_TRADE_TYPE;
	//決済日時
	private Date ORDER_TRADE_DATE;	
	//注文日時
	private Date ORDER_DATE;
	//届け先_氏名
	private String ORDER_RECEIVE_NAME;
	//届け先_氏名（カナ）
	private String ORDER_RECEIVE_NAME_KANA;
	//届け先_メールアドレス
	private String ORDER_RECEIVE_EMAIL;
	//届け先_電話番号
	private String ORDER_RECEIVE_TEL;
	//届け先_郵便番号
	private String ORDER_RECEIVE_ZIPCODE;
	//届け先_都道府県
	private String ORDER_RECEIVE_ADD_1;
	//届け先_市区町村
	private String ORDER_RECEIVE_ADD_2;
	//届け先_丁目、番地、建物名
	private String ORDER_RECEIVE_ADD_3;
	//合計金額
	private int TOTAL_PRICE;
	//書名
	private String BOOK_NAME;
	//単価
	private int BOOK_PRICE;
	
	public int getORDER_NO() {
		return ORDER_NO;
	}
	public void setORDER_NO(int oRDER_NO) {
		ORDER_NO = oRDER_NO;
	}
	public int getORDER_ITEM_NO() {
		return ORDER_ITEM_NO;
	}
	public void setORDER_ITEM_NO(int oRDER_ITEM_NO) {
		ORDER_ITEM_NO = oRDER_ITEM_NO;
	}
	public int getORDER_BOOK_NO() {
		return ORDER_BOOK_NO;
	}
	public void setORDER_BOOK_NO(int oRDER_BOOK_NO) {
		ORDER_BOOK_NO = oRDER_BOOK_NO;
	}
	public int getORDER_STATUS() {
		return ORDER_STATUS;
	}
	public void setORDER_STATUS(int oRDER_STATUS) {
		ORDER_STATUS = oRDER_STATUS;
	}
	public int getORDER_COUNT() {
		return ORDER_COUNT;
	}
	public void setORDER_COUNT(int oRDER_COUNT) {
		ORDER_COUNT = oRDER_COUNT;
	}
	public String getORDER_MEMBER_ID() {
		return ORDER_MEMBER_ID;
	}
	public void setORDER_MEMBER_ID(String oRDER_MEMBER_ID) {
		ORDER_MEMBER_ID = oRDER_MEMBER_ID;
	}
	public String getORDER_MEMO() {
		return ORDER_MEMO;
	}
	public void setORDER_MEMO(String oRDER_MEMO) {
		ORDER_MEMO = oRDER_MEMO;
	}
	public String getORDER_TRADE_TYPE() {
		return ORDER_TRADE_TYPE;
	}
	public void setORDER_TRADE_TYPE(String oRDER_TRADE_TYPE) {
		ORDER_TRADE_TYPE = oRDER_TRADE_TYPE;
	}
	public Date getORDER_TRADE_DATE() {
		return ORDER_TRADE_DATE;
	}
	public void setORDER_TRADE_DATE(Date oRDER_TRADE_DATE) {
		ORDER_TRADE_DATE = oRDER_TRADE_DATE;
	}
	public Date getORDER_DATE() {
		return ORDER_DATE;
	}
	public void setORDER_DATE(Date oRDER_DATE) {
		ORDER_DATE = oRDER_DATE;
	}
	public String getORDER_RECEIVE_NAME() {
		return ORDER_RECEIVE_NAME;
	}
	public void setORDER_RECEIVE_NAME(String oRDER_RECEIVE_NAME) {
		ORDER_RECEIVE_NAME = oRDER_RECEIVE_NAME;
	}
	public String getORDER_RECEIVE_NAME_KANA() {
		return ORDER_RECEIVE_NAME_KANA;
	}
	public void setORDER_RECEIVE_NAME_KANA(String oRDER_RECEIVE_NAME_KANA) {
		ORDER_RECEIVE_NAME_KANA = oRDER_RECEIVE_NAME_KANA;
	}
	public String getORDER_RECEIVE_EMAIL() {
		return ORDER_RECEIVE_EMAIL;
	}
	public void setORDER_RECEIVE_EMAIL(String oRDER_RECEIVE_EMAIL) {
		ORDER_RECEIVE_EMAIL = oRDER_RECEIVE_EMAIL;
	}
	public String getORDER_RECEIVE_TEL() {
		return ORDER_RECEIVE_TEL;
	}
	public void setORDER_RECEIVE_TEL(String oRDER_RECEIVE_TEL) {
		ORDER_RECEIVE_TEL = oRDER_RECEIVE_TEL;
	}
	public String getORDER_RECEIVE_ZIPCODE() {
		return ORDER_RECEIVE_ZIPCODE;
	}
	public void setORDER_RECEIVE_ZIPCODE(String oRDER_RECEIVE_ZIPCODE) {
		ORDER_RECEIVE_ZIPCODE = oRDER_RECEIVE_ZIPCODE;
	}
	public String getORDER_RECEIVE_ADD_1() {
		return ORDER_RECEIVE_ADD_1;
	}
	public void setORDER_RECEIVE_ADD_1(String oRDER_RECEIVE_ADD_1) {
		ORDER_RECEIVE_ADD_1 = oRDER_RECEIVE_ADD_1;
	}
	public String getORDER_RECEIVE_ADD_2() {
		return ORDER_RECEIVE_ADD_2;
	}
	public void setORDER_RECEIVE_ADD_2(String oRDER_RECEIVE_ADD_2) {
		ORDER_RECEIVE_ADD_2 = oRDER_RECEIVE_ADD_2;
	}
	public String getORDER_RECEIVE_ADD_3() {
		return ORDER_RECEIVE_ADD_3;
	}
	public void setORDER_RECEIVE_ADD_3(String oRDER_RECEIVE_ADD_3) {
		ORDER_RECEIVE_ADD_3 = oRDER_RECEIVE_ADD_3;
	}
	public int getTOTAL_PRICE() {
		return TOTAL_PRICE;
	}
	public void setTOTAL_PRICE(int tOTAL_PRICE) {
		TOTAL_PRICE = tOTAL_PRICE;
	}
	public String getBOOK_NAME() {
		return BOOK_NAME;
	}
	public void setBOOK_NAME(String bOOK_NAME) {
		BOOK_NAME = bOOK_NAME;
	}
	public int getBOOK_PRICE() {
		return BOOK_PRICE;
	}
	public void setBOOK_PRICE(int bOOK_PRICE) {
		BOOK_PRICE = bOOK_PRICE;
	}
	
}