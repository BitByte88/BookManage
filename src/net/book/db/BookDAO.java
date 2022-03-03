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
	//図書リスト取得
	public List<BookBean> item_List(int page,SearchBean searchBean) {
		List<BookBean> itemList = new ArrayList<>();
		
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
			findQuery.append("BOOK WHERE DELETE_FLAG = 0 ORDER BY BOOK_NO DESC) as book ");
			findQuery.append("WHERE book.rowNum>=? AND book.rowNum<=? ");	
			//タイトル検索の入力値がある場合
        	if(searchBean.getBOOK_NAME() != null && !searchBean.getBOOK_NAME().isEmpty()) {
        		findQuery.append("AND book.BOOK_NAME LIKE ? ");
        	}
        	//出版社検索の入力値がある場合
        	if(searchBean.getBOOK_PUBLISHER() != null && !searchBean.getBOOK_PUBLISHER().isEmpty()) {
        		findQuery.append("AND book.BOOK_PUBLISHER LIKE ? ");
        	}
        	//発行日検索の入力値（開始）がある場合
        	if(searchBean.getSTART_DATE() != null && !searchBean.getSTART_DATE().isEmpty()) {
        		findQuery.append("AND book.BOOK_PUBLISHING_DATE >= ? ");
        	}
        	//タイトル検索の入力値（終了）がある場合
        	if(searchBean.getEND_DATE() != null && !searchBean.getEND_DATE().isEmpty()) {
        		findQuery.append("AND book.BOOK_PUBLISHING_DATE <= ? ");
        	}
			pstmt = con.prepareStatement(findQuery.toString(), 
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY );
				int count = 1;
				//startnumからendnumまでの図書情報取得
				pstmt.setInt(count++, startnum);
				pstmt.setInt(count++, endnum);	
				//タイトル検索の入力値がある場合
	        	if(searchBean.getBOOK_NAME() != null && !searchBean.getBOOK_NAME().isEmpty()) {
	        		pstmt.setString(count++,'%'+searchBean.getBOOK_NAME()+'%');
	        	}
	        	//出版社検索の入力値がある場合
	        	if(searchBean.getBOOK_PUBLISHER() != null && !searchBean.getBOOK_PUBLISHER().isEmpty()) {
	        		pstmt.setString(count++,'%'+searchBean.getBOOK_PUBLISHER()+'%');
	        	}
	        	//発行日検索の入力値（開始）がある場合
	        	if(searchBean.getSTART_DATE() != null && !searchBean.getSTART_DATE().isEmpty()) {
	        		pstmt.setString(count++,searchBean.getSTART_DATE()+" 00:00:00");
	        	}
	        	//タイトル検索の入力値（終了）がある場合
	        	if(searchBean.getEND_DATE() != null && !searchBean.getEND_DATE().isEmpty()) {
	        		pstmt.setString(count++,searchBean.getEND_DATE()+" 23:59:59");
	        	}
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BookBean bookbean = new BookBean();
				//図書NO
				bookbean.setBOOK_NO(rs.getInt("BOOK_NO"));
				//カテゴリー
				bookbean.setBOOK_CATEGORY(
						rs.getString("BOOK_CATEGORY"));
				//書名
				bookbean.setBOOK_NAME(rs.getString("BOOK_NAME"));
				//販売価格
				bookbean.setBOOK_PRICE(rs.getInt("BOOK_PRICE"));
				
				StringTokenizer st=new StringTokenizer(
						rs.getString("BOOK_IMAGE"),",");
				String firstImg=st.nextToken();	
				//画像
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

	
	//該当図書の前もしくは後の図書情報を取得する。
	public BookBean findDetailNextPrev(int book_no,String direction) {
		BookBean book=new BookBean();
		StringBuffer dQuery = new StringBuffer();
		//次図書
		if(direction.equals("next")){
			dQuery.append("SELECT * FROM BOOK ");
			dQuery.append("WHERE BOOK_NO < ? AND DELETE_FLAG = 0 ");
			dQuery.append("ORDER BY BOOK_NO DESC ");
		}
        //前図書
		else if(direction.equals("prev")){
			dQuery.append("SELECT * FROM BOOK ");
			dQuery.append("WHERE BOOK_NO > ? AND DELETE_FLAG = 0");
		}
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(dQuery.toString(), 
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY );
			pstmt.setInt(1, book_no);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				//図書NO
				book.setBOOK_NO(rs.getInt("BOOK_NO"));
				//カテゴリー
				book.setBOOK_CATEGORY(
						rs.getString("BOOK_CATEGORY"));
				//書名
				book.setBOOK_NAME(rs.getString("BOOK_NAME"));
				//画像
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
	//図書詳細情報取得
	public BookBean findDetail(int book_num){
		BookBean book=new BookBean();
		
		try {
			con = ds.getConnection();
			StringBuffer dQuery = new StringBuffer();
		
			dQuery.append("SELECT * ");		
			dQuery.append("FROM BOOK WHERE BOOK_NO=? AND DELETE_FLAG = 0");
			
			pstmt = con.prepareStatement(dQuery.toString(), 
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY );
				pstmt.setInt(1, book_num);
			
			rs = pstmt.executeQuery();			
			
			if (rs.next()) {
				//図書NO
				book.setBOOK_NO(rs.getInt("BOOK_NO"));
				//カテゴリー
				book.setBOOK_CATEGORY(rs.getString("BOOK_CATEGORY"));
				//書名
				book.setBOOK_NAME(rs.getString("BOOK_NAME"));
				//著者
				book.setBOOK_WRITER(rs.getString("BOOK_WRITER"));
				//出版社
				book.setBOOK_PUBLISHER(rs.getString("BOOK_PUBLISHER"));
				//発行日
				book.setBOOK_PUBLISHING_DATE(rs.getDate("BOOK_PUBLISHING_DATE"));
				//図書内容
				book.setBOOK_CONTENT(rs.getString("BOOK_CONTENT"));
				//販売価格
				book.setBOOK_PRICE(rs.getInt("BOOK_PRICE"));
				//画像
				book.setBOOK_IMAGE(rs.getString("BOOK_IMAGE"));		
				//ISBNコード
				book.setBOOK_ISBN(rs.getString("BOOK_ISBN"));	
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
	//図書情報件数取得
	public int getCount() {
		int count=0;
		
		StringBuffer findQuery = new StringBuffer();
		
		findQuery.append("SELECT COUNT(BOOK_NO) FROM BOOK WHERE DELETE_FLAG=0");
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