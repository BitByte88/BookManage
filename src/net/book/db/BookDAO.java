package net.book.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class BookDAO {
	DataSource ds;
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	public BookDAO(){
		try{
			Context initCtx=new InitialContext();
			Context envCtx=(Context)initCtx.lookup("java:comp/env");
			ds=(DataSource)envCtx.lookup("jdbc/mysql");
		}catch(Exception ex){
			System.out.println("DB接続エラー：　" + ex);
			return;
		}
	}
	
	public List item_List(int page,SearchBean searchBean) {
		List itemList = new ArrayList();
		
		int startnum=page*12-11;
		int endnum=page*12;
		
		try {
			con = ds.getConnection();
			StringBuffer findQuery = new StringBuffer();
			findQuery.append("SELECT * FROM ");
			findQuery.append("(SELECT ROW_NUMBER() OVER (ORDER BY BOOK_NO DESC) AS rowNum, ");
			findQuery.append("BOOK_NO, BOOK_CATEGORY, BOOK_NAME, ");
			findQuery.append("BOOK_WRITER, BOOK_PUBLISHER, BOOK_PUBLISHING_DATE, ");
			findQuery.append("BOOK_CONTENT, BOOK_PRICE, BOOK_IMAGE, BOOK_ISBN FROM ");
			findQuery.append("BOOK ORDER BY BOOK_NO DESC) as book ");
			findQuery.append("WHERE book.rowNum>=? AND book.rowNum<=? ");		
        	if(searchBean.getBOOK_NAME() != null && !searchBean.getBOOK_NAME().isEmpty()) {
        		findQuery.append("AND book.BOOK_NAME LIKE ? ");
        	}
        	if(searchBean.getBOOK_PUBLISHER() != null && !searchBean.getBOOK_PUBLISHER().isEmpty()) {
        		findQuery.append("AND book.BOOK_PUBLISHER LIKE ? ");
        	}
        	if(searchBean.getSTART_DATE() != null && !searchBean.getSTART_DATE().isEmpty()) {
        		findQuery.append("AND book.BOOK_PUBLISHING_DATE >= ? ");
        	}
        	if(searchBean.getEND_DATE() != null && !searchBean.getEND_DATE().isEmpty()) {
        		findQuery.append("AND book.BOOK_PUBLISHING_DATE <= ? ");
        	}
			pstmt = con.prepareStatement(findQuery.toString(), 
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY );
				int count = 1;
				pstmt.setInt(count++, startnum);
				pstmt.setInt(count++, endnum);	
	        	if(searchBean.getBOOK_NAME() != null && !searchBean.getBOOK_NAME().isEmpty()) {
	        		pstmt.setString(count++,'%'+searchBean.getBOOK_NAME()+'%');
	        	}
	        	if(searchBean.getBOOK_PUBLISHER() != null && !searchBean.getBOOK_PUBLISHER().isEmpty()) {
	        		pstmt.setString(count++,'%'+searchBean.getBOOK_PUBLISHER()+'%');
	        	}
	        	if(searchBean.getSTART_DATE() != null && !searchBean.getSTART_DATE().isEmpty()) {
	        		pstmt.setString(count++,searchBean.getSTART_DATE()+" 00:00:00");
	        	}
	        	if(searchBean.getEND_DATE() != null && !searchBean.getEND_DATE().isEmpty()) {
	        		pstmt.setString(count++,searchBean.getEND_DATE()+" 23:59:59");
	        	}
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BookBean bookbean = new BookBean();
				bookbean.setBOOK_NO(rs.getInt("BOOK_NO"));
				bookbean.setBOOK_CATEGORY(
						rs.getString("BOOK_CATEGORY"));
				bookbean.setBOOK_NAME(rs.getString("BOOK_NAME"));
				bookbean.setBOOK_PRICE(rs.getInt("BOOK_PRICE"));
				
				StringTokenizer st=new StringTokenizer(
						rs.getString("BOOK_IMAGE"),",");
				String firstImg=st.nextToken();					 					
				bookbean.setBOOK_IMAGE(firstImg);									
				itemList.add(bookbean);
			} 		
			
			return itemList;
		} catch(SQLException e) {
			e.printStackTrace(); 
		}
		finally{
			try{
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				if(con!=null)con.close();
			}catch(Exception ex) {}
		}
		return itemList;
	}

	
	
	public BookBean findDetail(int book_num, String item, 
			String price,String direction) {
		BookBean book=new BookBean();
		
		int firstprice=0;
		int secondprice=0;
		
		if(price.equals("1~3")) {
			firstprice=1;
			secondprice=29999;
		} else if (price.equals("3~5")) {
			firstprice=30000;
			secondprice=49999;
			
		} else if (price.equals("5~7")) {
			firstprice=50000;
			secondprice=69999;
		} else if (price.equals("7~10")) {
			firstprice=70000;
			secondprice=99999;
		} else if (price.equals("10")){
			firstprice=100000;
			secondprice=999999;
		}
		
		StringBuffer dQuery = new StringBuffer();
		if(direction.equals("next")){
			dQuery.append("SELECT BOOK_NUM, BOOK_CATEGORY, ");
			dQuery.append("BOOK_IMAGE, BOOK_NAME FROM BOOK ");
			dQuery.append("WHERE BOOK_NUM>? AND ");
			if(item.equals("new_item")) {
				dQuery.append("BOOK_DATE>=DATE_SUB(SYSDATE(), INTERVAL 7 DAY) ");
			} else if (item.equals("hit_item")) {
				dQuery.append(" BOOK_BEST=1 ");
			} else {
				dQuery.append(" BOOK_CATEGORY=? ");			
			}
			if (!price.equals("no")) {
				dQuery.append("AND (BOOK_PRICE BETWEEN ? AND ? ) ");
			}
		}else if(direction.equals("prev")){
			dQuery.append(
			"SELECT BOOK_NUM, BOOK_CATEGORY, BOOK_IMAGE, ");
			dQuery.append(
			"BOOK_NAME FROM BOOK WHERE BOOK_NUM<? AND ");
			if(item.equals("new_item")) {
				dQuery.append("BOOK_DATE<=DATE_SUB(SYSDATE(), INTERVAL 7 DAY) ");
			} else if (item.equals("hit_item")) {
				dQuery.append("BOOK_BEST=1 ");
			} else {
				dQuery.append("BOOK_CATEGORY=? ");			
			}
			if (!price.equals("no")) {
				dQuery.append("AND (BOOK_PRICE BETWEEN ? AND ? ) ");
			}
			dQuery.append("ORDER BY BOOK_NUM DESC ");
		}
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(dQuery.toString(), 
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY );
			
			if (item.equals("new_item") || item.equals("hit_item")){
				if (price.equals("no")) {
					pstmt.setInt(1, book_num);
				} else {
					pstmt.setInt(1, book_num);
					pstmt.setInt(2, firstprice);
					pstmt.setInt(3, secondprice);
				}
			} else {
				if (price.equals("no")) {
					pstmt.setInt(1, book_num);
					pstmt.setString(2, item);
				} else{
					pstmt.setInt(1, book_num);
					pstmt.setString(2, item);
					pstmt.setInt(3, firstprice);
					pstmt.setInt(4, secondprice);
				}
			}
			
			rs = pstmt.executeQuery();
			if (rs.next()) {
				book.setBOOK_NO(rs.getInt("BOOK_NO"));
				book.setBOOK_CATEGORY(
						rs.getString("BOOK_CATEGORY"));
				book.setBOOK_NAME(rs.getString("BOOK_NAME"));
				StringTokenizer st=new StringTokenizer(
						rs.getString("BOOK_IMAGE"),",");
				book.setBOOK_IMAGE(st.nextToken());
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			try{
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				if(con!=null)con.close();
			}catch(Exception ex) {}
		}
		return book;	
	}
	
	public BookBean findDetailList(int book_num, String item){
		BookBean book=new BookBean();
		
		try {
			con = ds.getConnection();
			StringBuffer dQuery = new StringBuffer();
		
			dQuery.append("SELECT * ");		
			dQuery.append("FROM BOOK WHERE BOOK_NUM=? ");
			
			pstmt = con.prepareStatement(dQuery.toString(), 
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY );
				pstmt.setInt(1, book_num);
			
			rs = pstmt.executeQuery();			
			
			if (rs.next()) {	
				book.setBOOK_NO(rs.getInt("BOOK_NUM"));
				book.setBOOK_CATEGORY(
						rs.getString("BOOK_CATEGORY"));
				book.setBOOK_NAME(rs.getString("BOOK_NAME"));
				book.setBOOK_CONTENT(
						rs.getString("BOOK_CONTENT"));
				book.setBOOK_PRICE(rs.getInt("BOOK_PRICE"));
				book.setBOOK_IMAGE(rs.getString("BOOK_IMAGE"));			
			}
			

			return book;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		finally{
			try{
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				if(con!=null)con.close();
			}catch(Exception ex) {}
		}
		return null;
	}
	
	public int getCount(String item) {
		int count=0;
		
		StringBuffer findQuery = new StringBuffer();
		
		findQuery.append("SELECT COUNT(BOOK_NO) FROM BOOK ");
		try{
			con = ds.getConnection();
			pstmt=con.prepareStatement(findQuery.toString());
			rs=pstmt.executeQuery();
			rs.next();
			
			count=rs.getInt(1);

		}catch (Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				if(con!=null)con.close();
			}catch(Exception ex) {}
		}
		return count;
	}
	
}