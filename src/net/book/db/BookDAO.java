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
			System.out.println("DB ���� ���� : " + ex);
			return;
		}
	}
	
	public List item_List(String item, int page) {
		List itemList = new ArrayList();
		
		int startnum=page*12-11;
		int endnum=page*12;
		
		try {
			con = ds.getConnection();
			StringBuffer findQuery = new StringBuffer();
			
			findQuery.append("SELECT * FROM (SELECT BOOK_NUM,");
			findQuery.append("BOOK_CATEGORY, BOOK_NAME, ");
			findQuery.append("BOOK_CONTENT,BOOK_PRICE,BOOK_IMAGE,");
			findQuery.append("BOOK_BEST,BOOK_DATE, rownum r FROM ");
			findQuery.append("BOOK WHERE ");
			
			if (item.equals("new_item")) {
				findQuery.append("BOOK_DATE>=sysdate-7");
			}else if (item.equals("hit_item")) { 
				findQuery.append("BOOK_BEST=1 ");
			}else{
				findQuery.append("BOOK_CATEGORY=? ");
			}
			findQuery.append("ORDER BY BOOK_NUM DESC) ");
			findQuery.append("WHERE r>=? AND r<=? ");		
			
			pstmt = con.prepareStatement(findQuery.toString(), 
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY );
			
			if (item.equals("new_item") || item.equals("hit_item")){
				pstmt.setInt(1, startnum);
				pstmt.setInt(2, endnum);
			}else {
				pstmt.setString(1, item);
				pstmt.setInt(2, startnum);
				pstmt.setInt(3, endnum);
			}
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BookBean bookbean = new BookBean();
				bookbean.setBOOK_NUM(rs.getInt("BOOK_NUM"));
				bookbean.setBOOK_CATEGORY(
						rs.getString("BOOK_CATEGORY"));
				bookbean.setBOOK_NAME(rs.getString("BOOK_NAME"));
				bookbean.setBOOK_PRICE(rs.getInt("BOOK_PRICE"));
				
				StringTokenizer st=new StringTokenizer(
						rs.getString("BOOK_IMAGE"),",");
				String firstImg=st.nextToken();					 					
				bookbean.setBOOK_IMAGE(firstImg);				
				bookbean.setBOOK_BEST(rs.getInt("BOOK_BEST"));
								
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
	
	public List item_List(String item, int page, String searchprice) {
		List itemList=new ArrayList();
		int startnum=page*12-11;
		int endnum=page*12;
		
		int firstprice=0;
		int secondprice=0;
		
		if(searchprice.equals("1~3")){
			firstprice=1;
			secondprice=29999;
		} else if (searchprice.equals("3~5")) {
			firstprice=30000;
			secondprice=49999;
		} else if (searchprice.equals("5~7")) {
			firstprice=50000;
			secondprice=69999;
		} else if (searchprice.equals("7~10")) {
			firstprice=70000;
			secondprice=99999;
		} else {
			firstprice=100000;
			secondprice=999999;
		}
		
		try {
			con=ds.getConnection();
			StringBuffer findQuery = new StringBuffer();
			
			findQuery.append("SELECT * FROM (SELECT BOOK_NUM, ");
			findQuery.append("BOOK_CATEGORY, BOOK_NAME, ");
			findQuery.append("BOOK_CONTENT,BOOK_PRICE,BOOK_IMAGE,");
			findQuery.append("BOOK_BEST, rownum r FROM BOOK WHERE ");
			if (item.equals("new_item")){
				findQuery.append("BOOK_DATE>=sysdate-7");
			}else if (item.equals("hit_item")) { 
				findQuery.append("BOOK_BEST=1 ");
			}else {
				findQuery.append("BOOK_CATEGORY=? ");
			}
			findQuery.append(" AND (BOOK_PRICE BETWEEN ? AND ? ) ");
			findQuery.append("ORDER BY BOOK_NUM DESC) ");
			findQuery.append("WHERE r>=? AND r<=? ");		
			
			pstmt = con.prepareStatement(findQuery.toString(), 
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY );
			
			if (item.equals("new_item") || item.equals("hit_item")) {
				pstmt.setInt(1, firstprice);
				pstmt.setInt(2, secondprice);
				pstmt.setInt(3, startnum);
				pstmt.setInt(4, endnum);
			} else {
				pstmt.setString(1, item);
				pstmt.setInt(2, firstprice);
				pstmt.setInt(3, secondprice);
				pstmt.setInt(4, startnum);
				pstmt.setInt(5, endnum);
			}
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BookBean bookbean = new BookBean();
				bookbean.setBOOK_NUM(rs.getInt("BOOK_NUM"));
				bookbean.setBOOK_CATEGORY(
						rs.getString("BOOK_CATEGORY"));
				bookbean.setBOOK_NAME(rs.getString("BOOK_NAME"));
				bookbean.setBOOK_PRICE(rs.getInt("BOOK_PRICE"));
				
				StringTokenizer st=new StringTokenizer(
						rs.getString("BOOK_IMAGE"),",");
				String firstimg=st.nextToken();
				
				bookbean.setBOOK_IMAGE(firstimg);
				bookbean.setBOOK_BEST(rs.getInt("BOOK_BEST"));
				
				itemList.add(bookbean);
			} 		
				
			return itemList;
		} catch(SQLException e){
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
			dQuery.append("SELECT BOOK_NUM,BOOK_CATEGORY,");
			dQuery.append("BOOK_IMAGE,BOOK_NAME FROM BOOK ");
			dQuery.append("WHERE BOOK_NUM>? AND ");
			if(item.equals("new_item")) {
				dQuery.append("BOOK_DATE>=sysdate-7");
			} else if (item.equals("hit_item")) {
				dQuery.append(" BOOK_BEST=1 ");
			} else {
				dQuery.append(" BOOK_CATEGORY=? ");			
			}
			if (!price.equals("no")) {
				dQuery.append(" AND (BOOK_PRICE BETWEEN ? AND ? ) ");
			}
		}else if(direction.equals("prev")){
			dQuery.append(
			"SELECT BOOK_NUM,BOOK_CATEGORY,BOOK_IMAGE,");
			dQuery.append(
			"BOOK_NAME FROM BOOK WHERE BOOK_NUM<? AND ");
			if(item.equals("new_item")) {
				dQuery.append("BOOK_DATE<=sysdate-7");
			} else if (item.equals("hit_item")) {
				dQuery.append(" BOOK_BEST=1 ");
			} else {
				dQuery.append(" BOOK_CATEGORY=? ");			
			}
			if (!price.equals("no")) {
				dQuery.append(" AND (BOOK_PRICE BETWEEN ? AND ? ) ");
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
				book.setBOOK_NUM(rs.getInt("BOOK_NUM"));
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
		
			dQuery.append("SELECT *");		
			dQuery.append(" FROM BOOK WHERE BOOK_NUM=?  AND ");
			
			if(item.equals("new_item")) {
				dQuery.append("BOOK_DATE>=sysdate-7");
			} else if (item.equals("hit_item")) {
				dQuery.append("BOOK_BEST=1 ");
			} else {
				dQuery.append("BOOK_CATEGORY=? ");	
			}
			
			pstmt = con.prepareStatement(dQuery.toString(), 
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY );
			if (item.equals("new_item") || item.equals("hit_item")){
				pstmt.setInt(1, book_num);
			} else {
				pstmt.setInt(1, book_num);
				pstmt.setString(2, item);
			}
			
			rs = pstmt.executeQuery();			
			
			if (rs.next()) {	
				book.setBOOK_NUM(rs.getInt("BOOK_NUM"));
				book.setBOOK_CATEGORY(
						rs.getString("BOOK_CATEGORY"));
				book.setBOOK_NAME(rs.getString("BOOK_NAME"));
				book.setBOOK_CONTENT(
						rs.getString("BOOK_CONTENT"));
				book.setBOOK_SIZE(rs.getString("BOOK_SIZE"));
				book.setBOOK_COLOR(rs.getString("BOOK_COLOR"));
				book.setBOOK_AMOUNT(rs.getInt("BOOK_AMOUNT"));
				book.setBOOK_PRICE(rs.getInt("BOOK_PRICE"));
				book.setBOOK_IMAGE(rs.getString("BOOK_IMAGE"));
				book.setBOOK_BEST(rs.getInt("BOOK_BEST"));					
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
		
		findQuery.append("SELECT COUNT(BOOK_NUM) FROM BOOK WHERE ");
		
		if (item.equals("new_item")) {
			findQuery.append("BOOK_DATE>=sysdate-7");
		} else if (item.equals("hit_item")) { 
			findQuery.append("BOOK_BEST=? ");
		}else {
			findQuery.append("BOOK_CATEGORY=?");
		}
		
		try{
			con = ds.getConnection();
			pstmt=con.prepareStatement(findQuery.toString());
			if (item.equals("new_item")){
			}else if (item.equals("hit_item")) {
				pstmt.setInt(1,1);
			}else{
				pstmt.setString(1, item);
			}
			
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
	
	public int getCount(String item, String searchprice) {
		int count=0;
		int firstprice=0;
		int secondprice=0;
		
		if(searchprice.equals("1~3")) {
			firstprice=1;
			secondprice=29999;
		} else if (searchprice.equals("3~5")) {
			firstprice=30000;
			secondprice=49999;
		} else if (searchprice.equals("5~7")) {
			firstprice=50000;
			secondprice=69999;
		} else if (searchprice.equals("7~10")) {
			firstprice=70000;
			secondprice=99999;
		} else {
			firstprice=100000;
			secondprice=999999;
		}
		
		StringBuffer findQuery = new StringBuffer();
		
		findQuery.append("SELECT COUNT(BOOK_NUM) FROM BOOK WHERE ");
		if (item.equals("new_item")) {
			findQuery.append("BOOK_DATE>=sysdate-7");
		} else if (item.equals("hit_item")) { 
			findQuery.append("BOOK_BEST=? ");
		}else {
			findQuery.append("BOOK_CATEGORY=?");
		}
		findQuery.append(" and (book_price between ? and ?)");
		
		try
		{
			con = ds.getConnection();
			pstmt=con.prepareStatement(findQuery.toString());
			
			if(item.equals("new_item")){
				pstmt.setInt(1, firstprice);
				pstmt.setInt(2, secondprice);
			}else if (item.equals("hit_item")) {
				pstmt.setInt(1, 1);
				pstmt.setInt(2, firstprice);
				pstmt.setInt(3, secondprice);
			}else{
				pstmt.setString(1, item);
				pstmt.setInt(2, firstprice);
				pstmt.setInt(3, secondprice);
			}
			
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