package net.book.db;

public class SearchBean {
	private String BOOK_NAME;
	private String BOOK_PUBLISHER;
	private String START_DATE;
	private String END_DATE;


	public String getBOOK_NAME() {
		return BOOK_NAME;
	}
	public void setBOOK_NAME(String book_name) {
		BOOK_NAME = book_name;
	}

	public String getBOOK_PUBLISHER() {
		return BOOK_PUBLISHER;
	}
	public void setBOOK_PUBLISHER(String book_publisher) {
		BOOK_PUBLISHER = book_publisher;
	}

	public String getSTART_DATE() {
		return START_DATE;
	}
	public void setSTART_DATE(String start_date) {
		START_DATE = start_date;
	}

	public String getEND_DATE() {
		return END_DATE;
	}
	public void setEND_DATE(String end_date) {
		END_DATE = end_date;
	}
	
}