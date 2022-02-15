package net.admin.book.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
public class AdminBookDAO {
	DataSource ds;
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	public AdminBookDAO(){
		try{
			Context initCtx=new InitialContext();
			   Context envCtx=(Context)initCtx.lookup("java:comp/env");
			   ds=(DataSource)envCtx.lookup("jdbc/mysql");
			  
		}catch(Exception ex){
			System.out.println("DB接続エラー：　" + ex);
			return;
		}
	}
	public List<BookBean> getBookList() {
		List<BookBean> booklist = new ArrayList<>();
		try {
			con = ds.getConnection();
			String sql="select * from book where delete_flag = 0 order by book_no";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				BookBean bookbean = new BookBean();
				bookbean.setBOOK_NO(rs.getInt("book_no"));
				bookbean.setBOOK_CATEGORY(rs.getString("book_category"));
				bookbean.setBOOK_NAME(rs.getString("book_name"));
				bookbean.setBOOK_WRITER(rs.getString("book_writer"));
				bookbean.setBOOK_PUBLISHER(rs.getString("book_publisher"));
				bookbean.setBOOK_PUBLISHING_DATE(rs.getDate("book_publishing_date"));
				bookbean.setBOOK_CONTENT(rs.getString("book_content"));
				bookbean.setBOOK_PRICE(rs.getBigDecimal("book_price"));
				bookbean.setBOOK_IMAGE(rs.getString("book_image"));
				bookbean.setBOOK_ISBN(rs.getString("book_isbn"));
				booklist.add(bookbean);
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
		finally{
			try{
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				if(con!=null)con.close();
			}catch(Exception ex) {}
		}
		return booklist;
	}
	public BookBean getBook(int num) {
		BookBean abb = null;
		try {
			con = ds.getConnection();
			String sql="select * from book where book_no=? and delete_flag=0";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,num);
			rs=pstmt.executeQuery();
			
			if(rs.next()){
				abb=new BookBean();
				abb.setBOOK_NO(rs.getInt("book_no"));
				abb.setBOOK_CATEGORY(rs.getString("book_category"));
				abb.setBOOK_NAME(rs.getString("book_name"));
				abb.setBOOK_WRITER(rs.getString("book_writer"));
				abb.setBOOK_PUBLISHER(rs.getString("book_publisher"));
				abb.setBOOK_PUBLISHING_DATE(rs.getDate("book_publishing_date"));
				abb.setBOOK_CONTENT(rs.getString("book_content"));
				abb.setBOOK_PRICE(rs.getBigDecimal("book_price"));
				abb.setBOOK_IMAGE(rs.getString("book_image"));
				abb.setBOOK_ISBN(rs.getString("book_isbn"));
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

		return abb;
	}
	
	public int getBookNo() {
		int num = 0;
		String sql="select max(book_no) from book";
		try {
		con = ds.getConnection();
		pstmt=con.prepareStatement(sql);
		rs=pstmt.executeQuery();
		rs.next();
		num=rs.getInt(1)+1;	
		} catch (Exception e) {
			e.printStackTrace();
		} 
		finally{
			try{
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				if(con!=null)con.close();
			}catch(Exception ex) {}
		}
		return num;
	}
	
	public int insertBook(BookBean abb, int num) {
		int result = 0;
		try {
			con = ds.getConnection();
			String sql="insert into book values "+
			"(?,?,?,?,?,?,?,?,?,?,0,1,NOW(),1,NOW())";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setString(2, abb.getBOOK_CATEGORY());
			pstmt.setString(3, abb.getBOOK_NAME());
			pstmt.setString(4, abb.getBOOK_WRITER());
			pstmt.setString(5, abb.getBOOK_PUBLISHER());
			pstmt.setDate(6, abb.getBOOK_PUBLISHING_DATE());
			pstmt.setString(7, abb.getBOOK_CONTENT());
			pstmt.setBigDecimal(8, abb.getBOOK_PRICE());
			pstmt.setString(9, abb.getBOOK_IMAGE());
			pstmt.setString(10, abb.getBOOK_ISBN());
			result = pstmt.executeUpdate();
			} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try{
				if(pstmt!=null)pstmt.close();
				if(con!=null)con.close();
			}catch(Exception ex) {}
		}
		return result;
	}
	
	public int deleteBook(BookBean abb){
		int result = 0;
		try {
			con = ds.getConnection();
			String sql="update book set delete_flag=1 where book_no=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, abb.getBOOK_NO());
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.getStackTrace();
		}
		finally{
			try{
				if(pstmt!=null)pstmt.close();
				if(con!=null)con.close();
			}catch(Exception ex) {}
		}
		return result;
	}
	public int modifyBook(BookBean abb, int num) {
		int result = 0;
		try {
			con = ds.getConnection();
			String sql="update book set "+
			"book_category=?, book_name=?, book_writer=?, book_publisher=?, " + 
			"book_publishing_date=?, book_content=?, book_price=?, " +
			"book_image=? ,book_isbn=?, update_date=NOW() " +
			"where book_no=? and delete_flag=0";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, abb.getBOOK_CATEGORY());
			pstmt.setString(2, abb.getBOOK_NAME());
			pstmt.setString(3, abb.getBOOK_WRITER());
			pstmt.setString(4, abb.getBOOK_PUBLISHER());
			pstmt.setDate(5, abb.getBOOK_PUBLISHING_DATE());
			pstmt.setString(6, abb.getBOOK_CONTENT());
			pstmt.setBigDecimal(7, abb.getBOOK_PRICE());
			pstmt.setString(8, abb.getBOOK_IMAGE());
			pstmt.setString(9, abb.getBOOK_ISBN());
			pstmt.setInt(10, num);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try{
				if(pstmt!=null)pstmt.close();
				if(con!=null)con.close();
			}catch(Exception ex) {}
		}
		return result;
	}
}